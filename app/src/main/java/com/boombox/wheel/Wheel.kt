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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
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

    //2) infinite Scrolling Wheels
    LaunchedEffect(Unit) {
        scope.launch {
            state.scrollToItem(days.size*100/2,(-state.layoutInfo.viewportSize.height/2))//add scroll half list top exactly to center of huge list
        }
    }
    //3) snap-to-Center Behavior
    LaunchedEffect(state.isScrollInProgress) {
        if (!state.isScrollInProgress) {
            val mid = state.layoutInfo.viewportSize.height/2
            val closetItem = state.layoutInfo.visibleItemsInfo.minByOrNull {
                abs(it.offset +(it.size/2)- mid)
            }?: return@LaunchedEffect
            scope.launch { state.scrollBy(
                (closetItem.offset +(closetItem.size/2) - mid).toFloat()
            ) }
        }
    }
    LazyColumn(
        state = state,
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 50.dp, height = 120.dp)
            .drawWithContent{//3) we create some layouts for middle item
                drawContent()//our content
                drawRect(//top alpha
                    topLeft = Offset(0f,size.height/2),
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent,Color.Black),
                        startY = size.height/2,
                        endY = size.height
                    )
                )
                drawRect(//bottom alpha
                    topLeft = Offset(0f,0f),
                    size = Size(size.width,size.height/2),
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Black,Color.Transparent),
                        startY = 0f,
                        endY = size.height/2
                    )
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //1) Infinite list
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