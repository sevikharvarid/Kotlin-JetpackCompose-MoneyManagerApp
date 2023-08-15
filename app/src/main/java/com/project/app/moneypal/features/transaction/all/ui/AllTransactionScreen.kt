package com.project.app.moneypal.features.transaction.all.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.project.app.moneypal.R
import com.project.app.moneypal.features.transaction.summary.ui.TransactionItem
import com.project.app.moneypal.features.transaction.summary.ui.getAccountDisplay
import com.project.app.moneypal.features.transaction.summary.ui.getAmountColor
import com.project.app.moneypal.features.transaction.summary.ui.getAmountDisplay
import com.project.app.moneypal.features.transaction.summary.ui.getDateTimeDisplay
import com.project.app.moneypal.features.transaction.summary.ui.getTitle
import com.project.app.moneypal.foundation.extension.getSymbol
import com.project.app.moneypal.foundation.theme.AlphaDisabled
import com.project.app.moneypal.foundation.theme.DividerAlpha
import com.project.app.moneypal.foundation.uicomponent.PgAmountLabel
import com.project.app.moneypal.foundation.uicomponent.PgContentTitle
import com.project.app.moneypal.foundation.uicomponent.PgDateLabel
import com.project.app.moneypal.foundation.uicomponent.PgEmpty
import com.project.app.moneypal.foundation.uicomponent.PgIcon
import com.project.app.moneypal.foundation.uicomponent.PgIconButton
import com.project.app.moneypal.foundation.uicomponent.PgPageLayout
import com.wisnu.foundation.coredatetime.toLocalDateTime
import com.wisnu.foundation.coredatetime.toMillis
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset


@Composable
fun AllTransactionScreen(
    viewModel: AllTransactionViewModel,
    onTransactionItemClick: (String) -> Unit,
    onCancelClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle()
    val localFocusManager = LocalFocusManager.current

    AllTransactionScreen(
        onCancelClick={
            localFocusManager.clearFocus()
            onCancelClick()
        },
        state = state,
        onTransactionItemClick = {
            onTransactionItemClick(it.transactionId)
        },
        onDateClick = {
            viewModel.dispatch(AllTransactionAction.ShowDatePicker)
        },
        onCloseDatePickerClick = {
            viewModel.dispatch(AllTransactionAction.DismissDatePicker)
        },
        onClickDateSelect ={
            viewModel.dispatch(AllTransactionAction.SelectDate(it))
        }
    )
}

@Composable
private fun AllTransactionScreen(
    state: AllTransactionState,
    onTransactionItemClick: (TransactionItem) -> Unit,
    onCancelClick: () -> Unit,
    onDateClick: () -> Unit,
    onCloseDatePickerClick: () -> Unit,
    onClickDateSelect: (LocalDateTime?) -> Unit,
    ) {
    PgPageLayout(
        modifier = Modifier.fillMaxSize()
    ) {

        Content(
            state = state,
            onTransactionItemClick = onTransactionItemClick,
            onCancelClick = onCancelClick,
            onDateClick = onDateClick,
            onCloseDatePickerClick = onCloseDatePickerClick,
            onClickDateSelect = onClickDateSelect,
        )
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
private fun Content(
    state: AllTransactionState,
    onTransactionItemClick: (TransactionItem) -> Unit,
    onCancelClick: () -> Unit,
    onDateClick: () -> Unit,
    onCloseDatePickerClick: () -> Unit,
    onClickDateSelect: (LocalDateTime?) -> Unit,
    ) {
    val searchTextState = remember { mutableStateOf("") }

    val filteredTransactionItems = state.transactionItems.filter{
        it.note.lowercase().contains(searchTextState.value.lowercase())
    }.filter {
        if (state.alreadySelect) {
            it.getDateTimeDisplay() == state.transactionDateDisplayable()
        } else {
            true
        }
    }

    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Color.White,
        title = {

            Row(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.all_transaction_headline),
                    style = MaterialTheme.typography.headlineSmall
                )
                PgIconButton(
                    onClick = onDateClick,
                    color = Color.Transparent)
                {
                    PgIcon(imageVector = Icons.Rounded.CalendarMonth)
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onCancelClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Button"
                )
            }
        }
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            SpacerSection()
        }

        item{
            SearchAppBar(
                text = searchTextState.value,
                onTextChange = {
                    searchTextState.value = it
                               },
                onCloseClicked = {
                    searchTextState.value = ""
                },
                onSearchClicked = {
                },
            )
        }

        item {
            SpacerSection()
        }


        item {
            GeneralSection(
                showDatePicker = state.showDatePicker,
                transactionDateInitial = state.transactionDate,
                onClickDateCancel = onCloseDatePickerClick,
                onClickDateSelect = onClickDateSelect,
            )
        }

        TransactionCell(
            data = filteredTransactionItems,
            onItemClick = onTransactionItemClick,
        )

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

private inline fun LazyListScope.TransactionCell(
    data: List<TransactionItem>,
    noinline onItemClick: (TransactionItem) -> Unit,
) {
    if (data.isEmpty()) {
        item {
            PgEmpty(
                stringResource(R.string.all_transaction_empty),
                modifier = Modifier.height(500.dp)
            )
        }
    } else {
        itemsIndexed(
            items = data,
            key = { _, item -> item.transactionId }
        ) { _, item ->
            TransactionItemCell(
                title = item.getTitle(),
                account = item.getAccountDisplay(),
                dateTime = item.getDateTimeDisplay(),
                amount = item.getAmountDisplay(),
                amountSymbol = item.currency.getSymbol(),
                amountColor = item.getAmountColor(
                    MaterialTheme.colorScheme.onBackground
                ),
                note = item.note,
                onClick = {
                    val currentDate = LocalDate.now()
                    Log.d("Data :",item.date.toString())
//                    Log.d("Data :",currentDate.toString())
                    onItemClick(item)
                }
            )
        }
    }
}

@Composable
private fun TransactionItemCell(
    title: String,
    account: String,
    dateTime: String,
    amount: String,
    amountSymbol: String,
    amountColor: Color,
    note: String,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, bottom = 2.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                PgContentTitle(
                    text = title,
                    modifier = Modifier.padding(end = 4.dp)
                )

                PgDateLabel(
                    text = dateTime,
                )
            }

            PgAmountLabel(
                amount = amount,
                symbol = amountSymbol,
                color = amountColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 2.dp, end = 16.dp),
            )

            PgContentTitle(
                text = account,
                color = MaterialTheme.colorScheme.onBackground.copy(AlphaDisabled),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 2.dp, end = 16.dp),
            )

            PgContentTitle(
                text = note,
                color = MaterialTheme.colorScheme.onBackground.copy(AlphaDisabled),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            )

            Divider(
                modifier = Modifier.padding(start = 16.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = DividerAlpha)
            )
        }
    }
}

@Composable
private fun SpacerSection() {
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,

) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Cari berdasarkan keterangan",
                    color = Color.LightGray
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = Color.Black
            ),
            singleLine = true,
            enabled = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.LightGray
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.LightGray
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GeneralSection(
    showDatePicker: Boolean,
    transactionDateInitial: LocalDateTime,
    onClickDateCancel: () -> Unit,
    onClickDateSelect: (LocalDateTime?) -> Unit,
) {

    if (showDatePicker) {
        val datePickerState = remember {
            DatePickerState(
                initialSelectedDateMillis = transactionDateInitial.toMillis(ZoneId.ofOffset("UTC", ZoneOffset.UTC)),
                initialDisplayedMonthMillis = transactionDateInitial.toMillis(ZoneId.ofOffset("UTC", ZoneOffset.UTC)),
                yearRange = DatePickerDefaults.YearRange,
                initialDisplayMode = DisplayMode.Picker
            )
        }
        val confirmEnabled by remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
        DatePickerDialog(
            onDismissRequest = onClickDateCancel,
            confirmButton = {
                TextButton(
                    onClick = {
                        onClickDateSelect(datePickerState.selectedDateMillis?.toLocalDateTime())
                    },
                    enabled = confirmEnabled
                ) { Text("Oke") }
            },
            dismissButton = {
                TextButton(
                    onClick = onClickDateCancel
                ) { Text(stringResource(R.string.transaction_edit_cancel)) }
            }
        ) {
            DatePicker(state = datePickerState, dateValidator = {
                val zone = ZoneId.ofOffset("UTC", ZoneOffset.UTC)
                val start = LocalDate.of(2000, 1, 1).toMillis(zone)
                val end = LocalDate.now().toMillis(zone)
                return@DatePicker it in start..end
            })
        }
    }

}
