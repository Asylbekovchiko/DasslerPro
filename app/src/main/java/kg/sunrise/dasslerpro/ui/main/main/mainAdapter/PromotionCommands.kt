package kg.sunrise.dasslerpro.ui.main.main.mainAdapter

interface PromotionCommands {

    val showBottomSheet: (String, String) -> Unit

    val onItemClick: (Int) -> Unit
}