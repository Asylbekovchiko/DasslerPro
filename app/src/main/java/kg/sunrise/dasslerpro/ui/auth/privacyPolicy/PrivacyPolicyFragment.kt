package kg.sunrise.dasslerpro.ui.auth.privacyPolicy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.barteksc.pdfviewer.link.DefaultLinkHandler
import com.github.barteksc.pdfviewer.util.FitPolicy
import kg.sunrise.dasslerpro.base.fragment.BaseFragmentWithVM
import kg.sunrise.dasslerpro.data.models.responses.PrivacyPoliceResponse
import kg.sunrise.dasslerpro.databinding.FragmentPrivacyPolicyBinding
import kg.sunrise.dasslerpro.ui.main.info.InfoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel
import java.net.URL


class PrivacyPolicyFragment :
    BaseFragmentWithVM<FragmentPrivacyPolicyBinding, InfoViewModel>() {

    override val viewModel: InfoViewModel by viewModel()
    override val progressBar: ConstraintLayout by lazy { binding.inclProgress.clProgress }

    override fun makeRequests() {
        viewModel.getPrivacyInfo()
    }

    override fun findTypeOfObject(data: Any?) {
        when (data) {
            is PrivacyPoliceResponse -> {
                loadPdf(data)
            }
        }
    }

    private fun loadPdf(pdf: PrivacyPoliceResponse) {
        lifecycleScope.launch {

            val stream = withContext(Dispatchers.IO) {
                return@withContext URL(pdf.pdfFileURL).openStream()
            }

            binding.pdfView.fromStream(stream)
                .enableSwipe(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .linkHandler(DefaultLinkHandler(binding.pdfView))
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true)
                .spacing(0)
                .pageFitPolicy(FitPolicy.WIDTH)
                .load()
        }
    }

    override fun successRequest() {
    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPrivacyPolicyBinding {
        return FragmentPrivacyPolicyBinding.inflate(inflater)
    }

    override fun init() {
        initListeners()
    }

    private fun initListeners() {
        binding.inclToolbar.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}