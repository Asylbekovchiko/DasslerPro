package kg.sunrise.dasslerpro.ui.main.main.promotionDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.fragment.BaseFragmentWithVM
import kg.sunrise.dasslerpro.data.models.responses.*
import kg.sunrise.dasslerpro.databinding.FragmentPromotionDetailBinding
import kg.sunrise.dasslerpro.ui.auth.AuthActivity
import kg.sunrise.dasslerpro.ui.customLayouts.bottomSheetDialogs.CouponCodeBottomSheetFragment
import kg.sunrise.dasslerpro.ui.main.main.PagingReloadViewModel
import kg.sunrise.dasslerpro.ui.shared.fragments.gallery.GalleryViewModel
import kg.sunrise.dasslerpro.ui.shared.imageAdapter.ImageRatio
import kg.sunrise.dasslerpro.ui.shared.imageAdapter.ImagesAdapter
import kg.sunrise.dasslerpro.ui.shared.socialAdapter.SocialAdapter
import kg.sunrise.dasslerpro.utils.constants.preloadImagesCount
import kg.sunrise.dasslerpro.utils.extensions.*
import kg.sunrise.dasslerpro.utils.parsers.DateTimeParser
import kg.sunrise.dasslerpro.utils.parsers.DateTimePattern
import kg.sunrise.dasslerpro.utils.preference.isAuthorized
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class PromotionDetailFragment :
    BaseFragmentWithVM<FragmentPromotionDetailBinding, PromotionDetailViewModel>() {

    private val navArgs by navArgs<PromotionDetailFragmentArgs>()

    override val viewModel: PromotionDetailViewModel by viewModel()
    override val progressBar: ConstraintLayout by lazy {
        binding.inclProgress.clProgress
    }
    private val galleryViewModel: GalleryViewModel by sharedViewModel()

    private val pagingReloadViewModel: PagingReloadViewModel by sharedViewModel()

    private val couponBottomSheet by lazy {
        CouponCodeBottomSheetFragment() { couponCode ->
            viewModel.activateCoupon(navArgs.promotionId, couponCode)
        }
    }

    private val imageAdapter = ImagesAdapter(ImageRatio.PROMOTION_DETAIL) {
        navigateToGallery()
    }

    private val socialAdapter = SocialAdapter() { link ->
        navigateToSocial(link)
    }

    override fun makeRequests() {
        viewModel.getPromotionInfo(navArgs.promotionId)
    }

    override fun findTypeOfObject(data: Any?) {
        when (data) {
            is PromotionDetailResponse -> {
                handlePromotionInfo(data)
            }
            is CouponInfoResponse -> {
                handleCouponResponse(data)
            }
        }
    }

    private fun handleCouponResponse(couponInfo: CouponInfoResponse) {
        pagingReloadViewModel.isReloadPromotions = true
        couponBottomSheet.success(couponInfo) {
            findNavController().navigateUp()
        }
    }

    private fun handlePromotionInfo(promotionDetail: PromotionDetailResponse) {
        fillMainData(promotionDetail)
        fillImages(promotionDetail.promotionsImages)
        fillPartnerData(promotionDetail.partner)
        fillSocials(promotionDetail.partner.partnerSocials)
    }

    private fun fillImages(promotionsImages: ArrayList<PromotionsImage>) {
        if (promotionsImages.isNullOrEmpty()) {
            binding.ivPlaceholder.visible()
            return
        }

        imageAdapter.setData(ArrayList(promotionsImages.map { it.image }))
        galleryViewModel.imagesPath = imageAdapter.getImages()
        binding.indicator.visible()

        binding.indicator.text =
            getString(
                R.string.position_amount,
                galleryViewModel.getPageByPosition,
                imageAdapter.itemCount
            )
    }

    private fun fillSocials(partnerSocials: ArrayList<PartnerSocial>) {
        if (partnerSocials.isNullOrEmpty()) return
        binding.clSocial.visible()
        socialAdapter.setData(ArrayList(partnerSocials.map { it.getSocialDto() }))
    }

    private fun fillPartnerData(partner: Partner) {
        binding.apply {
            Glide.with(this@PromotionDetailFragment)
                .load(partner.logo)
                .into(ivPartnerIcon)

            tvPartnerName.text = partner.title
            tvPartnerContent.setHtml(partner.description)
        }
    }

    private fun fillMainData(promotionDetail: PromotionDetailResponse) {
        binding.apply {
            tvTitle.text = promotionDetail.title
            tvDesc.setHtml(promotionDetail.description)
            tvBeginDate.text = getString(R.string.award_begin_date, DateTimeParser.formatDateTime(promotionDetail.startDate, DateTimePattern.yyyy_MM_dd_with_dash, DateTimePattern.dd_MM_yyy_with_dots))
            tvEndDate.text = getString(R.string.award_end_date, DateTimeParser.formatDateTime(promotionDetail.endDate, DateTimePattern.yyyy_MM_dd_with_dash, DateTimePattern.dd_MM_yyy_with_dots))
        }
    }

    override fun successRequest() {

    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPromotionDetailBinding {
        return FragmentPromotionDetailBinding.inflate(inflater)
    }

    override fun init() {
        setImageAdapter()
        setSocialAdapter()
        initListeners()
    }

    @SuppressLint("WrongConstant")
    private fun setImageAdapter() {
        val viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                galleryViewModel.position = position
                binding.indicator.text =
                    getString(
                        R.string.position_amount,
                        galleryViewModel.getPageByPosition,
                        imageAdapter.itemCount
                    )
            }
        }

        binding.vpSlider.apply {
            adapter = imageAdapter
            offscreenPageLimit = preloadImagesCount

            binding.indicator.text =
                getString(
                    R.string.position_amount,
                    galleryViewModel.getPageByPosition,
                    imageAdapter.itemCount
                )

            setOverScrollModeNever()
        }.post {
            binding.vpSlider.currentItem = galleryViewModel.position
            binding.vpSlider.registerOnPageChangeCallback(viewPagerCallback)
        }
    }

    private fun setSocialAdapter() {
        binding.rvSocials.adapter = socialAdapter
    }

    override fun onDetach() {
        galleryViewModel.clearData()

        super.onDetach()
    }

    private fun initListeners() {
        binding.inclToolbar.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnParticipate.setOnClickListener {
            if (isAuthorized(requireContext())) {
                showDialog()
            } else {
                navigateToAuth()
            }
        }
    }

    private fun showDialog() {
        couponBottomSheet.show(parentFragmentManager, null)
    }

    private fun navigateToGallery() {
        val action =
            PromotionDetailFragmentDirections.actionPromotionDetailFragmentToGalleryFragment()
        findNavController().navigate(action)
    }

    private fun navigateToAuth() {
        val intent = Intent(requireContext(), AuthActivity::class.java)
        startActivity(intent)
        requireActivity().transitionFade()
    }

    override fun showErrorAsDialog(message: String) {
        couponBottomSheet.showError(message)
    }
}