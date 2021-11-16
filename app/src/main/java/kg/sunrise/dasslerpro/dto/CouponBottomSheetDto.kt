package kg.sunrise.dasslerpro.dto

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CouponBottomSheetDto(
    @DrawableRes
    val iconRes: Int,
    val text: String,
    @StringRes
    val iconTextRes: Int? = null
)