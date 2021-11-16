package kg.sunrise.dasslerpro.ui.main.info

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.fragment.BaseFragmentWithVM
import kg.sunrise.dasslerpro.data.models.responses.AboutPhone
import kg.sunrise.dasslerpro.data.models.responses.AboutSocial
import kg.sunrise.dasslerpro.data.models.responses.AboutUsInfoResponse
import kg.sunrise.dasslerpro.databinding.FragmentInfoBinding
import kg.sunrise.dasslerpro.ui.main.info.phoneNumberAdapter.PhoneNumberAdapter
import kg.sunrise.dasslerpro.ui.shared.fragments.gallery.GalleryViewModel
import kg.sunrise.dasslerpro.ui.shared.imageAdapter.ImageRatio
import kg.sunrise.dasslerpro.ui.shared.imageAdapter.ImagesAdapter
import kg.sunrise.dasslerpro.ui.shared.socialAdapter.SocialAdapter
import kg.sunrise.dasslerpro.utils.constants.preloadImagesCount
import kg.sunrise.dasslerpro.utils.extensions.navigateToSocial
import kg.sunrise.dasslerpro.utils.extensions.setHtml
import kg.sunrise.dasslerpro.utils.extensions.setOverScrollModeNever
import kg.sunrise.dasslerpro.utils.extensions.visible
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class InfoFragment : BaseFragmentWithVM<FragmentInfoBinding, InfoViewModel>() {

    override val viewModel: InfoViewModel by viewModel()
    override val progressBar: ConstraintLayout by lazy { binding.inclProgress.clProgress }

    private val galleryViewModel: GalleryViewModel by sharedViewModel()

    private val phoneAdapter = PhoneNumberAdapter()
    private val imageAdapter = ImagesAdapter(ImageRatio.ABOUT_US) {
        navigateToGallery()
    }

    private val socialAdapter = SocialAdapter() { link ->
        navigateToSocial(link)
    }

    override fun makeRequests() {
        if (!viewModel.hasInternet || viewModel.aboutUsInfo == null)
            viewModel.getInfoAboutUs()
        else {
            findTypeOfObject(viewModel.aboutUsInfo)
        }
    }

    override fun findTypeOfObject(data: Any?) {
        when (data) {
            is AboutUsInfoResponse -> {
                handleAboutUsInfo(data)
            }
        }
    }

    private fun handleAboutUsInfo(info: AboutUsInfoResponse) {
        fillMainData(info)
        fillImages(ArrayList(info.images.map { it.image }))
        setPhonesVisibility(info.phones)
        fillSocials(info.socials)
    }

    private fun setPhonesVisibility(phones: ArrayList<AboutPhone>) {
        if (phones.isNullOrEmpty()) return

        binding.clPhones.visible()
        phoneAdapter.setData(ArrayList(phones.map { it.phone }))
    }

    private fun fillImages(images: ArrayList<String>) {
        if (images.isNullOrEmpty()) {
            binding.ivPlaceholder.visible()
            return
        }

        imageAdapter.setData(images)
        galleryViewModel.imagesPath = imageAdapter.getImages()
        binding.indicator.visible()

        binding.indicator.text =
            getString(
                R.string.position_amount,
                galleryViewModel.getPageByPosition,
                imageAdapter.itemCount
            )
    }

    private fun fillMainData(info: AboutUsInfoResponse) {
        binding.apply {
            tvBigTitle.text = info.title
            tvSmallTitle.text = info.title
            info.description?.let {
                tvDesc.setHtml(it)
            }
        }
    }

    override fun successRequest() {
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentInfoBinding {
        return FragmentInfoBinding.inflate(inflater)
    }

    override fun init() {
        initListeners()
        setImagesAdapter()
        setSocialAdapter()
    }

    private fun initListeners() {

    }

    @SuppressLint("WrongConstant")
    private fun setImagesAdapter() {
        binding.rvPhoneNumbers.adapter = phoneAdapter

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

    private fun fillSocials(socials: ArrayList<AboutSocial>) {
        if (socials.isNullOrEmpty()) return
        binding.clSocial.visible()
        socialAdapter.setData(ArrayList(socials.map { it.getSocialDto() }))
    }

    private fun navigateToGallery() {
        val action = InfoFragmentDirections.actionInfoFragmentToGalleryFragment()
        findNavController().navigate(action)
    }
}