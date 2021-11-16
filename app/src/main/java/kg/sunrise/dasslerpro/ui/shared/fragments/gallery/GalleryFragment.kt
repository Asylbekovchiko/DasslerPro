package kg.sunrise.dasslerpro.ui.shared.fragments.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.fragment.BaseFragment
import kg.sunrise.dasslerpro.databinding.FragmentGalleryBinding
import kg.sunrise.dasslerpro.ui.shared.imageAdapter.SharedImagesAdapter
import kg.sunrise.dasslerpro.utils.extensions.setOverScrollModeNever
import org.koin.android.viewmodel.ext.android.sharedViewModel


class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {

    private val galleryViewModel: GalleryViewModel by sharedViewModel()

    private val imageAdapter = SharedImagesAdapter()

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGalleryBinding {
        return FragmentGalleryBinding.inflate(inflater)
    }

    override fun init() {
        setAdapter()
        initListeners()
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setAdapter() {
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
            imageAdapter.setData(galleryViewModel.imagesPath)

            binding.indicator.text =
                getString(
                    R.string.position_amount,
                    galleryViewModel.getPageByPosition,
                    imageAdapter.itemCount
                )

            setOverScrollModeNever()
        }.post {
            binding.vpSlider.setCurrentItem(galleryViewModel.position, true)
            binding.vpSlider.registerOnPageChangeCallback(viewPagerCallback)
        }
    }
}