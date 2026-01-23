package com.boombox.wheel

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.math.abs

@Composable
fun Wheel(
    list: List<String>,
    currentIndex: Int = 0,
    selectedIndex: (Int) -> Unit
) {
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            state.scrollToItem(
                ( currentIndex + list.size * 500),
                (state.layoutInfo.visibleItemsInfo.first().size/2 -state.layoutInfo.viewportSize.height / 2)
            )
        }
    }
    LaunchedEffect(state.isScrollInProgress) {
        if (!state.isScrollInProgress) {
            val mid = state.layoutInfo.viewportSize.height / 2
            val closestItem = state.layoutInfo.visibleItemsInfo.minByOrNull {
                abs(it.offset + (it.size / 2) - mid)
            } ?: return@LaunchedEffect

            scope.launch {
                state.animateScrollBy(
                    (closestItem.offset + (closestItem.size / 2) - mid).toFloat()
                )
            }
            selectedIndex(closestItem.index % list.size)
        }
    }
    LazyColumn(
        state = state,
        modifier = Modifier
            .size(width = 60.dp, height = 90.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(list.size * 1000) { i ->
            val index = i % list.size
            Text(
                modifier = Modifier.padding(3.dp),
                text = list[index],
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun DateWheel(
    initialDateTime: LocalDateTime = LocalDateTime.now(),
    onDateTimeChanged: (LocalDateTime) -> Unit = {}
) {
    var selectedYear   by remember { mutableIntStateOf(initialDateTime.year) }
    var selectedMonth  by remember { mutableIntStateOf(initialDateTime.monthValue - 1) }
    var selectedDay    by remember { mutableIntStateOf(initialDateTime.dayOfMonth) }

    val years = (1900..2199).map { it.toString() }
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    val days = remember(selectedYear, selectedMonth) {
        val getDaysInMonth: (Int, Int) -> Int = { month, year ->
            when (month) {
                3, 5, 8, 10 -> 30
                1 -> if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29 else 28
                else -> 31
            }
        }
        mutableStateListOf(*(1..getDaysInMonth(selectedMonth, selectedYear)).map { it.toString() }.toTypedArray())
    }
    LaunchedEffect(selectedYear, selectedMonth, selectedDay) {
        val dateTime = LocalDateTime.of(
            selectedYear,
            selectedMonth + 1,
            selectedDay,
            0,
            0
        )
        onDateTimeChanged(dateTime)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
            Wheel(
                list = years,
                currentIndex = initialDateTime.year - 1900
            ) { index ->
                selectedYear = 1900 + index
            }
            Wheel(
                list = months,
                currentIndex = initialDateTime.monthValue - 1
            ) { index ->
                selectedMonth = index
            }
            Wheel(
                list = days,
                currentIndex = initialDateTime.dayOfMonth -1
            ) { index ->
                selectedDay = index + 1
            }
    }
}