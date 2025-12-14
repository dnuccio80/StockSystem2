package com.danucdev.stocksystem.ui.screens.mainpanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.danucdev.stocksystem.DarkFontColor

@Composable
fun MainPanel() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        Text("Panel Principal", fontSize = 32.sp, color = DarkFontColor, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun Canchas() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        Text("Canchas", fontSize = 32.sp, color = DarkFontColor, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun CuentasCorrientes() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        Text("Cuentas Corrientes", fontSize = 32.sp, color = DarkFontColor, fontWeight = FontWeight.Bold)
    }
}