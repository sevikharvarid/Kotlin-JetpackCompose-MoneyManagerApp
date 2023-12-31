package com.project.app.moneypal.runtime.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import com.project.app.moneypal.foundation.theme.LargeRadius
import com.project.app.moneypal.foundation.theme.SmallRadius

@Immutable
data class BottomSheetConfig(
    val sheetShape: Shape,
    val showScrim: Boolean
)

val DefaultBottomSheetConfig = BottomSheetConfig(
    RoundedCornerShape(
        topStart = LargeRadius,
        topEnd = LargeRadius
    ),
    true
)
val NoScrimMainBottomSheetConfig = BottomSheetConfig(
    RoundedCornerShape(
        topStart = LargeRadius,
        topEnd = LargeRadius
    ),
    false
)
val NoScrimSmallShapeMainBottomSheetConfig = BottomSheetConfig(
    RoundedCornerShape(
        topStart = SmallRadius,
        topEnd = SmallRadius
    ),
    false
)

