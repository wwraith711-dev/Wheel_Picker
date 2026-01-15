package com.boombox.wheel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Wheel() {

    val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 50.dp, height = 120.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(days.size){ index ->
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