package com.danucdev.stocksystem.ui.screens.turns

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.danucdev.stocksystem.DarkFontColor
import com.danucdev.stocksystem.ui.core.ButtonTextItem
import com.danucdev.stocksystem.ui.core.ScreenTitle

class TurnsScreen: Screen {
    @Composable
    override fun Content() {
        Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                ScreenTitle("Turnos del día")
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ButtonTextItem("Agendar turno") { }
                }
            }
        }
    }

}

