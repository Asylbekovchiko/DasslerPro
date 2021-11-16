package kg.sunrise.dasslerpro.ui.shared.fragments.gallery

import kg.sunrise.dasslerpro.base.viewModel.BaseViewModel

class GalleryViewModel : BaseViewModel() {

    var position = DEFAULT_POSITION_VALUE

    /**
     * Current page is default image position + 1
     */
    val getPageByPosition
        get() = position + 1

    var imagesPath = arrayListOf<String>()

    fun clearData() {
        position = DEFAULT_POSITION_VALUE
        imagesPath = arrayListOf()
    }

    companion object {
        const val DEFAULT_POSITION_VALUE = 0
    }
}