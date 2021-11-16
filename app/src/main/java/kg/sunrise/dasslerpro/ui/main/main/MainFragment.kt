package kg.sunrise.dasslerpro.ui.main.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import kg.sunrise.dasslerpro.base.fragment.BaseFragmentWithPaging
import kg.sunrise.dasslerpro.base.paging.pagingLoadState.PagingLoadStateAdapter
import kg.sunrise.dasslerpro.databinding.FragmentMainBinding
import kg.sunrise.dasslerpro.ui.customLayouts.bottomSheetDialogs.PromotionCrownInfoBottomSheetFragment
import kg.sunrise.dasslerpro.ui.main.main.mainAdapter.PromotionAdapter
import kg.sunrise.dasslerpro.ui.main.main.mainAdapter.PromotionCommands
import org.koin.android.viewmodel.ext.android.viewModel
import kg.sunrise.dasslerpro.data.models.responses.PromotionResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MainFragment : BaseFragmentWithPaging<PromotionResponse, FragmentMainBinding, PromotionViewModel>() {

    override val viewModel: PromotionViewModel by viewModel()
    override val progressBar: ConstraintLayout by lazy { binding.inclProgress.clProgress }

    private val pagingReloadViewModel: PagingReloadViewModel by sharedViewModel()

    private val promotionCommands = object : PromotionCommands {
        override val showBottomSheet: (String, String) -> Unit = { title, description ->
            PromotionCrownInfoBottomSheetFragment(title, description).show(parentFragmentManager, null)
        }
        override val onItemClick: (Int) -> Unit = { navigateToPromotionDetail(it) }
    }

    private val promotionAdapter = PromotionAdapter(promotionCommands)

    override fun init() {
        setAdapter()
        initListeners()
    }

    private fun initListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            swipeRefresh()
        }
    }

    private fun setAdapter() {
        binding.rvPromotions.adapter = promotionAdapter
            .withLoadStateFooter(PagingLoadStateAdapter { promotionAdapter.retry() })

        lifecycleScope.launch {
            promotionAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect()
        }
    }

    override fun makeRequests() {
        viewModel.clearDataIfNeed()

        if (pagingReloadViewModel.isReloadPromotions)
            viewModel.clearPaging()

        getPromotions()
    }

    private fun swipeRefresh() {
        binding.swipeRefresh.isRefreshing = false

        viewModel.clearPaging()

        makeRequests()
    }

    private fun getPromotions() {
        requestJob = lifecycleScope.launch {
            viewModel.getPromotionsPaging().collectLatest {
                promotionAdapter.submitData(it)
            }
        }
    }

    override fun findTypeOfObject(data: Any?) {
    }

    override fun successRequest() {
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater)
    }

    private fun navigateToPromotionDetail(promotionId: Int) {
        val action = MainFragmentDirections.actionMainFragmentToPromotionDetailFragment(promotionId)
        findNavController().navigate(action)
    }
}