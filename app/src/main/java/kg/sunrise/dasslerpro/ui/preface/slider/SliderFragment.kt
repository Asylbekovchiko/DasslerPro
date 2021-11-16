package kg.sunrise.dasslerpro.ui.preface.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import kg.sunrise.dasslerpro.R
import kg.sunrise.dasslerpro.base.fragment.BaseFragment
import kg.sunrise.dasslerpro.databinding.FragmentSliderBinding
import kg.sunrise.dasslerpro.dto.SliderInfoDto
import kg.sunrise.dasslerpro.ui.preface.slider.sliderAdapter.SliderAdapter
import kg.sunrise.dasslerpro.utils.extensions.*

class SliderFragment : BaseFragment<FragmentSliderBinding>() {

    private val sliderAdapter = SliderAdapter()

    private val sliderData = arrayListOf(
        SliderInfoDto(R.drawable.ic_slider_1, R.string.slider_desc_1),
        SliderInfoDto(R.drawable.ic_slider_2, R.string.slider_desc_2),
        SliderInfoDto(R.drawable.ic_slider_3, R.string.slider_desc_3)
    )

    private var pagePosition = 0

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSliderBinding {
        return FragmentSliderBinding.inflate(inflater)
    }

    override fun init() {
        initAdapter()
        initListeners()
    }

    private fun initListeners() {
        binding.btnNext.setOnClickListener {
            if (pagePosition == sliderAdapter.itemCount - 1) {
                navigateToMain()
            } else {
                pagePosition++
                binding.vpSlider.currentItem = pagePosition
            }
        }

        binding.tvSkip.setOnClickListener {
            navigateToMain()
        }
    }

    private fun initAdapter() {
        binding.apply {
            vpSlider.adapter = sliderAdapter
            sliderAdapter.setData(sliderData)
            indicator.setViewPager2(vpSlider)
            vpSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    binding.tvSliderDesc.setText(sliderData[position].str_id)
                    this@SliderFragment.pagePosition = position

                    when (position) {
                        0 -> {
                            binding.tvSkip.visible()
                            binding.btnNext.setText(R.string.Next)
                        }
                        1 -> {
                            binding.tvSkip.visible()
                            binding.btnNext.setText(R.string.Continue)
                        }
                        2 -> {
                            binding.tvSkip.gone()
                            binding.btnNext.setText(R.string.Begin)
                        }
                    }
                }
            })

            vpSlider.setOverScrollModeNever()
        }
    }
}