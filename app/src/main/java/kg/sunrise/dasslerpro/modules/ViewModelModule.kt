package kg.sunrise.dasslerpro.modules

import kg.sunrise.dasslerpro.ui.auth.AuthorizationViewModel
import kg.sunrise.dasslerpro.ui.main.profile.ProfileViewModel
import kg.sunrise.dasslerpro.ui.main.handbook.HandbookViewModel
import kg.sunrise.dasslerpro.ui.main.info.InfoViewModel
import kg.sunrise.dasslerpro.ui.main.main.PagingReloadViewModel
import kg.sunrise.dasslerpro.ui.main.main.PromotionViewModel
import kg.sunrise.dasslerpro.ui.main.main.promotionDetail.PromotionDetailViewModel
import kg.sunrise.dasslerpro.ui.preface.PrefaceViewModel
import kg.sunrise.dasslerpro.ui.shared.fragments.gallery.GalleryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GalleryViewModel() }
    viewModel { PromotionViewModel(get()) }
    viewModel { PrefaceViewModel(get()) }
    viewModel { InfoViewModel(get()) }
    viewModel { PromotionDetailViewModel(get()) }
    viewModel { AuthorizationViewModel(get()) }
    viewModel { ProfileViewModel() }
    viewModel { HandbookViewModel(get()) }
    viewModel { PagingReloadViewModel() }
}