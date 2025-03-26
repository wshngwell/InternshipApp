package com.example.internshipapp.presentation.feature16

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyView(
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "textFirst"
            )
            Text(
                textAlign = TextAlign.Center,
                text = "textSecond"
            )
        }

    }

}



@Composable
@Preview
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
    ) {
        MyView(modifier = Modifier.background(Color.Yellow))
        Text("базовый")

        MyView(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
        )
        Text("на всю ширину")

        Row {
            MyView(
                modifier = Modifier
                    .background(Color.Red)
                    .weight(1f)
            )
            MyView(
                modifier = Modifier
                    .background(Color.Yellow)
                    .weight(1f)
            )
        }
        Text("2 вью на всю ширину экрана")
    }
}