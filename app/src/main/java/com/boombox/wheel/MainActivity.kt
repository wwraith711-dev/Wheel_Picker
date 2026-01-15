package com.boombox.wheel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.boombox.wheel.ui.theme.WheelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WheelTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { showDialog = true}
        ) { Text("Open Wheel")
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false},
            text = {
                Wheel()
            },
            confirmButton = {
                Button(
                    onClick = { }
                ) {
                    Text("Done")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false}
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}