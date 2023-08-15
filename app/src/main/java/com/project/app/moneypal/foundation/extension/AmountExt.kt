package com.project.app.moneypal.foundation.extension

import androidx.compose.ui.graphics.Color
import com.project.app.moneypal.foundation.theme.Expense
import com.project.app.moneypal.foundation.theme.Income
import java.math.BigDecimal

fun BigDecimal.getAmountColor(defaultColor: Color): Color {
    val zero = BigDecimal.ZERO
    return if (this > zero) {
        Income
    } else if (this < zero) {
        Expense
    } else {
        defaultColor
    }
}
