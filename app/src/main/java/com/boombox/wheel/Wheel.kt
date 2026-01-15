package com.boombox.wheel

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

@Composable
fun Wheel() {
    val state = rememberLazyListState() // remote controller for lazy Column
    val scope = rememberCoroutineScope() // this u can say battery for ur remote Controller
    val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    /*
    3) every time we open that wheel
    we'll jump to center of that huge list to make illusion that we are infinite wheel
     */
    LaunchedEffect(Unit) {
        scope.launch { state.scrollToItem(days.size*100/2) } // auto scroll to middle
    }
    LazyColumn(
        state = state,
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 50.dp, height = 120.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1) we make huge list [count *100]
        items(days.size*100){ i ->
            // 2) ofc we need to change this loop , we need to make nested loop
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