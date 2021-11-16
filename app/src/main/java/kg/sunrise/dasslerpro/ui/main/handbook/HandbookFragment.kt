package kg.sunrise.dasslerpro.ui.main.handbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kg.sunrise.dasslerpro.base.fragment.BaseFragmentWithVM
import kg.sunrise.dasslerpro.data.models.responses.HandbookResponse
import kg.sunrise.dasslerpro.databinding.FragmentHandbookBinding
import kg.sunrise.dasslerpro.utils.extensions.setHtml
import kg.sunrise.dasslerpro.utils.extensions.visible
import org.koin.android.viewmodel.ext.android.viewModel

class HandbookFragment :
    BaseFragmentWithVM<FragmentHandbookBinding, HandbookViewModel>() {

    override val viewModel: HandbookViewModel by viewModel()

    override val progressBar: ConstraintLayout by lazy {
        binding.inclProgress.clProgress
    }

    private lateinit var handbookAdapter: HandbookAdapter

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHandbookBinding = FragmentHandbookBinding.inflate(inflater)

    override fun init() {
        initRV()
    }

    private fun initRV() = with(binding.rvHandbooks) {
        handbookAdapter = HandbookAdapter()
        adapter = handbookAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun makeRequests() {
        if (!viewModel.hasInternet || viewModel.handbook == null)
            viewModel.getHandbooks()
        else {
            findTypeOfObject(viewModel.handbook)
        }
    }

    override fun findTypeOfObject(data: Any?) {
        if (data is HandbookResponse) {
            viewModel.handbook = data
            onHandbookResponse(data)
        }
    }

    private fun onHandbookResponse(
        handbookResponse: HandbookResponse
    ) = with(handbookResponse) {
        binding.tvHandbook.text = title
        binding.tvHandbookDescription.setHtml(description)
        if (handbookQuestions.isNullOrEmpty()) return@with

        binding.vDivider.visible()
        handbookAdapter.setData(handbookQuestions)
    }

    override fun successRequest() {
    }

}