package com.boombox.wheel

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
fun Wheel(
    list: List<String>,
    currentIndex: Int = 0,
    selectedIndex: (Int) -> Unit
) {
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            state.animateScrollToItem(currentIndex + list.size*100,(-state.layoutInfo.viewportSize.height/2))
        }
    }
    LaunchedEffect(state.isScrollInProgress) {
        if (!state.isScrollInProgress ) {
            val mid = state.layoutInfo.viewportSize.height/2
            val closetItem = state.layoutInfo.visibleItemsInfo.minByOrNull {
                abs(it.offset +(it.size/2)- mid)
            }?: return@LaunchedEffect
            scope.launch { state.animateScrollBy(
                (closetItem.offset +(closetItem.size/2) - mid).toFloat()
            ) }
            selectedIndex(closetItem.index % list.size)
        }
    }
    LazyColumn(
        state = state,
        flingBehavior = rememberSnapFlingBehavior(state),
        modifier = Modifier
            .size(width = 50.dp, height = 150.dp)
            .drawWithContent{
                drawContent()
                drawRect(
                    size = Size(size.width,size.height/2),
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Black,Color.Transparent),
                        endY = size.height/2
                    )
                )
                drawRect(
                    topLeft = Offset(0f,size.height/2),
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent,Color.Black),
                        startY = size.height/2,
                        endY = size.height
                    )
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(list.size*200){ i ->
            val index = i % list.size
            Text(
                modifier = Modifier
                    .padding(3.dp),
                text = list[index],
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}
