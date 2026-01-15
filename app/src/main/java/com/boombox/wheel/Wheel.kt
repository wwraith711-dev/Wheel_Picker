package com.boombox.wheel

import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun Wheel() {
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    LaunchedEffect(Unit) {
        scope.launch { state.scrollToItem(days.size*100/2) }
    }

    //1) here we make after every scroll it center the item to center
    LaunchedEffect(state.isScrollInProgress) {
        if (!state.isScrollInProgress) {// after lift the finger
            val mid = state.layoutInfo.viewportSize.height/2 // this the mid of the view part of list
            val closetItem = state.layoutInfo.visibleItemsInfo.minByOrNull {// minimum item who has smallest distance from mid
                abs(it.offset +(it.size/2)- mid) // distance from mid
            }?: return@LaunchedEffect
            scope.launch { state.scrollBy( //auto scroll it smallest distance item to center
                (closetItem.offset +(closetItem.size/2) - mid).toFloat()
            ) }
        }
    }
    LazyColumn(
        state = state,
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 50.dp, height = 120.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(days.size*100){ i ->
            val index = i % days.size
            Text(
                modifier = Modifier
                    .padding(3.dp),
                text = days[index],
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}