package com.project.app.moneypal.features.graph.ui

import android.os.Bundle
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditCalendar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.app.moneypal.R
import com.project.app.moneypal.features.transaction.all.ui.GraphSummaryState
import com.project.app.moneypal.features.transaction.summary.ui.getAccountDisplay
import com.project.app.moneypal.features.transaction.summary.ui.getAmountColor
import com.project.app.moneypal.features.transaction.summary.ui.getDateTimeDisplay
import com.project.app.moneypal.features.transaction.summary.ui.getTitle
import com.project.app.moneypal.foundation.extension.getColor
import com.project.app.moneypal.foundation.theme.AlphaDisabled
import com.project.app.moneypal.foundation.theme.DividerAlpha
import com.project.app.moneypal.foundation.uicomponent.PgAmountLabel
import com.project.app.moneypal.foundation.uicomponent.PgContentTitle
import com.project.app.moneypal.foundation.uicomponent.PgDateLabel
import com.project.app.moneypal.foundation.uicomponent.PgEmpty
import com.project.app.moneypal.foundation.uicomponent.PgHeadline1
import com.project.app.moneypal.foundation.uicomponent.PgPageLayout
import com.project.app.moneypal.foundation.uicomponent.RoundedLinearProgressIndicator
import com.project.app.moneypal.model.Account
import kotlin.math.PI
import kotlin.math.atan2
import androidx.compose.ui.graphics.*
//import com.project.app.moneypal.features.transaction.all.ui.TransactionCell
import com.project.app.moneypal.features.transaction.summary.ui.TransactionItem
import com.project.app.moneypal.features.transaction.summary.ui.getAmountDisplay
import com.project.app.moneypal.foundation.extension.getSymbol
import com.project.app.moneypal.foundation.uicomponent.MonthPicker
import com.project.app.moneypal.foundation.uicomponent.PgIcon
import com.project.app.moneypal.foundation.uicomponent.PgIconButton
import java.math.BigDecimal
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Calendar


@Composable
fun GraphSummaryScreen(
    viewModel: GraphSummaryViewModel,
    route: String?,
    arguments: Bundle?,
    onClickAccount: (String) -> Unit,
    onClickAddAccount: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isMenuExpanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(3) }

    //Month Picker Variables
    var visible by remember {
        mutableStateOf(false)
    }
    val visibleState = rememberUpdatedState(visible)
    var date by remember {
        mutableStateOf("")
    }
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
    val year = Calendar.getInstance().get(Calendar.YEAR)

    PgPageLayout(
        modifier = Modifier.fillMaxSize()
    ) {

        PgHeadline1(
            text = stringResource(R.string.dashboard_graph),
                modifier = Modifier
                    .fillMaxWidth()
        )
        Row(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
        ) {
            PgIconButton(onClick = {isMenuExpanded = !isMenuExpanded }, color = Color.White) {
                PgIcon(imageVector = Icons.Rounded.EditCalendar)
            }
            DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                DropdownMenuItem(onClick = {
                    selectedOption = 0
                    isMenuExpanded = false
                }) {
                    Text(text = "3 Hari Terakhir")
                }
                DropdownMenuItem(onClick = {
                    selectedOption = 1
                    isMenuExpanded = false
                }) {
                    Text(text = "Minggu lalu")
                }
                DropdownMenuItem(onClick = {
                    selectedOption = 2
                    isMenuExpanded = false
                }) {
                    Text(text = "Bulan lalu")
                }
                DropdownMenuItem(onClick = {
                    selectedOption = 3
                    isMenuExpanded = false
                }) {
                    Text(text = "Bulan ini")
                }
                DropdownMenuItem(onClick = {
                    selectedOption = 4
                    isMenuExpanded = false
                    visible = true
                }) {
                    Text(text = "Pilih Bulan")
                }
            }
        }
        GraphSummaryScreen(
            state = state,
            selectedOption = selectedOption,
            monthData = date,
            onClickAccount = {
                onClickAccount(it.id)
            },
            onClickAddAccount = onClickAddAccount
        )

        if (visibleState.value) {
            MonthPicker(
                visible = true,
                currentMonth = currentMonth,
                currentYear = year,
                confirmButtonCLicked = { month_, year_ ->
                    date = "$month_/$year_"
                    visible = false
                },
                cancelClicked = {
                    visible = false
                }
            )
        }
    }
}

fun formatTransactionDate(date: String): String {
    val parts = date.split("-")
    val month = parts[1].toIntOrNull()?.toString() ?: parts[0]
    val year = parts[0]
    return "$month/$year"
}

@Composable
private fun GraphSummaryScreen(
    state: GraphSummaryState,
    selectedOption: Int,
    monthData: String,
    onClickAccount: (Account) -> Unit,
    onClickAddAccount: () -> Unit,
) {

    val currentDate = LocalDate.now()
    val currentMonth = currentDate.monthValue
    val currentYear = currentDate.year
    val currentTimeMonth = "$currentMonth/$currentYear"

    val filteredTransactionItems = state.transactionItems.filter {
        val datePart = it.date.toString().substring(0, 10)
        when (selectedOption) {
            0 -> {
                val lastThreeDays = currentDate.minusDays(3)
                datePart >= lastThreeDays.toString()
            }
            1 -> {
                val lastWeek = currentDate.minusDays(7)
                datePart >= lastWeek.toString()
            }
            2 -> {
                val lastMonth = currentDate.minusMonths(1)
                datePart >= lastMonth.toString()
            }
            3 -> {
                val monthYearFormat = formatTransactionDate(datePart)
                monthYearFormat == currentTimeMonth
            }
            4 -> {
                val monthYearFormat = formatTransactionDate(datePart)
                monthYearFormat == monthData
            }
            else -> false
        }
    }

    val titleCountMap = filteredTransactionItems.groupingBy {
        it.categoryType.name
    }.eachCount()



    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            SpacerSection(height = 20.dp)
        }
        item {
            SpacerSection(height = 20.dp)
        }

        item{
            val pieChartDataList = mutableListOf<PieChartInput>()
            for ((title, count) in titleCountMap) {
                val percentage = count * 100 / filteredTransactionItems.size
                val floatPercentage = percentage / 100f

                pieChartDataList.add(
                    PieChartInput(
                        color = filteredTransactionItems.find { it.categoryType.name == title }?.categoryType?.getColor()
                            ?: Color.Black,
                        value = floatPercentage,
                        description = title
                    )
                )
            }
            if (pieChartDataList.size == 0) {
                PgEmpty(
                    stringResource(R.string.all_transaction_empty),
                    modifier = Modifier.height(500.dp)
                )
            } else {
                PieCharts(
                    modifier = Modifier.size(500.dp),
                    input = pieChartDataList,
                    centerText = "Persentase berdasarkan Kategori"
                )
            }
        }

        TransactionCell(
            data = filteredTransactionItems,
            onItemClick = {},
        )

        item {
            SpacerSection(height = 80.dp)
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
                progressColor = item.categoryType.getColor(),
                onClick = {}
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
    progressColor: Color
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 350.dp, bottom = 5.dp)
            ) {
                RoundedLinearProgressIndicator(
                    progress = 1F,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp),
                    trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = AlphaDisabled),
                    color = progressColor
                )
            }

            Divider(
                modifier = Modifier.padding(start = 16.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = DividerAlpha)
            )
        }
    }
}

@Composable
private fun SpacerSection(
    height: Dp
) {
    Spacer(modifier = Modifier.height(height))
}

data class PieChartInput(
    val color:Color,
    val value:Float,
    val description:String,
    val isTapped:Boolean = false
)

@Composable
private fun PieCharts(
    modifier: Modifier = Modifier,
    radius:Float = 450f,
    innerRadius:Float = 200f,
    transparentWidth:Float = 25f,
    input:List<PieChartInput>,
    centerText:String = ""
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var inputList by remember {
        mutableStateOf(input)
    }
    var isCenterTapped by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = { offset ->
                            val tapAngleInDegrees = (-atan2(
                                x = circleCenter.y - offset.y,
                                y = circleCenter.x - offset.x
                            ) * (180f / PI).toFloat() - 90f).mod(360f)
                            val centerClicked = if (tapAngleInDegrees < 90) {
                                offset.x < circleCenter.x + innerRadius && offset.y < circleCenter.y + innerRadius
                            } else if (tapAngleInDegrees < 180) {
                                offset.x > circleCenter.x - innerRadius && offset.y < circleCenter.y + innerRadius
                            } else if (tapAngleInDegrees < 270) {
                                offset.x > circleCenter.x - innerRadius && offset.y > circleCenter.y - innerRadius
                            } else {
                                offset.x < circleCenter.x + innerRadius && offset.y > circleCenter.y - innerRadius
                            }

                            if (centerClicked) {
                                inputList = inputList.map {
                                    it.copy(isTapped = !isCenterTapped)
                                }
                                isCenterTapped = !isCenterTapped
                            } else {
                                val anglePerValue = 360f / input.sumOf {
                                    it.value.toDouble()
                                }
                                var currAngle = 0f
                                inputList.forEach { pieChartInput ->

                                    currAngle += pieChartInput.value * anglePerValue.toFloat()
                                    if (tapAngleInDegrees < currAngle) {
                                        val description = pieChartInput.description
                                        inputList = inputList.map {
                                            if (description == it.description) {
                                                it.copy(isTapped = !it.isTapped)
                                            } else {
                                                it.copy(isTapped = false)
                                            }
                                        }
                                        return@detectTapGestures
                                    }
                                }
                            }
                        }
                    )
                }
        ){
            val width = size.width
            val height = size.height
            circleCenter = Offset(x= width/2f,y= height/2f)

            val totalValue = input.sumOf {
                it.value.toDouble()
            }
            val anglePerValue = 360f/totalValue
            var currentStartAngle = 0f

            inputList.forEach { pieChartInput ->
                val scale = if(pieChartInput.isTapped) 1.1f else 1.0f
                val angleToDraw = pieChartInput.value * anglePerValue
                scale(scale){
                    drawArc(
                        color = pieChartInput.color,
                        startAngle = currentStartAngle,
                        sweepAngle = angleToDraw.toFloat(),
                        useCenter = true,
                        size = Size(
                            width = radius*2f,
                            height = radius*2f
                        ),
                        topLeft = Offset(
                            (width-radius*2f)/2f,
                            (height - radius*2f)/2f
                        )
                    )
                    currentStartAngle += angleToDraw.toFloat()
                }
                var rotateAngle = currentStartAngle-angleToDraw/2f-90f
                var factor = 1f
                if(rotateAngle>90f){
                    rotateAngle = (rotateAngle+180).mod(360f)
                    factor = -0.92f
                }

                val percentage = (pieChartInput.value/totalValue.toFloat()*100)
                val formattedPercentage = "%.2f".format(percentage)

                drawContext.canvas.nativeCanvas.apply {
                    if(percentage>3){
                        rotate(rotateAngle.toFloat()){
                            drawText(
                                "$formattedPercentage %",
                                circleCenter.x,
                                circleCenter.y+(radius-(radius-innerRadius)/2f)*factor,
                                Paint().apply {
                                    textSize = 13.sp.toPx()
                                    textAlign = Paint.Align.CENTER
                                    color = Color.White.toArgb()
                                }
                            )
                        }
                    }
                }
                if(pieChartInput.isTapped){
                    val tabRotation = currentStartAngle - angleToDraw - 90f
                    rotate(tabRotation.toFloat()){
                        drawRoundRect(
                            topLeft = circleCenter,
                            size = Size(12f,radius*1.2f),
                            color = Color.Transparent,
                            cornerRadius = CornerRadius(15f,15f)
                        )
                    }
                    rotate((tabRotation+angleToDraw).toFloat()){
                        drawRoundRect(
                            topLeft = circleCenter,
                            size = Size(12f,radius*1.2f),
                            color = Color.Transparent,
                            cornerRadius = CornerRadius(15f,15f)
                        )
                    }
                    rotate(rotateAngle.toFloat()){
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "${pieChartInput.description}: ${pieChartInput.value}",
                                circleCenter.x,
                                circleCenter.y + radius*1.3f*factor,
                                Paint().apply {
                                    textSize = 22.sp.toPx()
                                    textAlign = Paint.Align.CENTER
                                    color = Color.White.toArgb()
                                    isFakeBoldText = true
                                }
                            )
                        }
                    }
                }
            }

            if(inputList.first().isTapped){
                rotate(-90f){
                    drawRoundRect(
                        topLeft = circleCenter,
                        size = Size(12f,radius*1.2f),
                        color = Color.Transparent,
                        cornerRadius = CornerRadius(15f,15f)
                    )
                }
            }
            drawContext.canvas.nativeCanvas.apply {
                drawCircle(
                    circleCenter.x,
                    circleCenter.y,
                    innerRadius,
                    Paint().apply {
                        color = Color.White.copy(alpha = 0.6f).toArgb()
                        setShadowLayer(10f,0f,0f, Color.Gray.toArgb())
                    }
                )
            }

            drawCircle(
                color = Color.White.copy(0.2f),
                radius = innerRadius+transparentWidth/2f
            )

        }
        Text(
            centerText,
            modifier = Modifier
                .width(Dp(innerRadius / 1.5f))
                .padding(25.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

    }
}