package com.project.app.moneypal.features.graph.ui

import android.os.Bundle
import java.time.LocalDateTime

sealed interface GraphSummaryAction {
    data class SelectDate(val selectedDate: LocalDateTime?) : GraphSummaryAction
    object DismissDatePicker : GraphSummaryAction
    object ShowDatePicker : GraphSummaryAction
}

