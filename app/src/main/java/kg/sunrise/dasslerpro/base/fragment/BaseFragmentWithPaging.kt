package kg.sunrise.dasslerpro.base.fragment

import androidx.viewbinding.ViewBinding
import kg.sunrise.dasslerpro.base.viewModel.BaseViewModelPaging
import kotlinx.coroutines.Job

abstract class BaseFragmentWithPaging<Item: Any, Binding : ViewBinding, out VM : BaseViewModelPaging<Item>>
    : BaseFragmentWithVM<Binding, VM>() {

    protected var requestJob: Job? = null

    override fun makeRequests() {
        viewModel.clearPaging()
    }
}