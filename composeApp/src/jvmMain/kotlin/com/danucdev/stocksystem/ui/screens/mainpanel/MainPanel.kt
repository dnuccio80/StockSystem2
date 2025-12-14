package com.danucdev.stocksystem.ui.screens.mainpanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout

@Composable
fun MainPanel() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Red)) {
        Text("Panel Principal")
    }
}


@Composable
fun Canchas() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Red)) {
        Text("Canchas")
    }
}

@Composable
fun CuentasCorrientes() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Red)) {
        Text("Cuentas Corrientes")
    }
}