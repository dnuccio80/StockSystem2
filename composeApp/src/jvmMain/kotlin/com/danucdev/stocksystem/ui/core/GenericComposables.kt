package com.danucdev.stocksystem.ui.core

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.danucdev.stocksystem.CardBackgroundSecond
import com.danucdev.stocksystem.DarkFontColor

@Composable
fun CardTitle(text: String) {
    Text(text, fontSize = 24.sp, color = DarkFontColor, fontWeight = FontWeight.Bold)
}

@Composable
fun ScreenTitle(text:String) {
    Text(text, fontSize = 32.sp, color = DarkFontColor, fontWeight = FontWeight.Bold)
}

@Composable
fun CardBody(text: String) {
    Text(text, fontSize = 14.sp, color = DarkFontColor)
}

@Composable
fun ButtonTextItem(text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() }, colors = ButtonDefaults.buttonColors(
            backgroundColor = CardBackgroundSecond
        )
    ) {
        Text(text, color = DarkFontColor)
    }
}

@Composable
fun AccentText(text:String, color: Color = Color.Green.copy(.6f)) {
    Text(
        "$32.500",
        fontSize = 36.sp,
        color = color,
        fontWeight = FontWeight.Bold
    )
}