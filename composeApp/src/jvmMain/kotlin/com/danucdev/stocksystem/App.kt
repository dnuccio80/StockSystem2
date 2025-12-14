package com.danucdev.stocksystem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

import stocksystem.composeapp.generated.resources.Res
import stocksystem.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {

        KoinApplication( application = { modules() }) {
            Box(
                modifier = Modifier.fillMaxSize().background(DarkAppBackground),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(64.dp)
                ) {
                    Card(
                        modifier =
                            Modifier
                                .fillMaxHeight()
                                .width(40.dp),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = DarkMenuBackground),
                        elevation = CardDefaults.cardElevation(16.dp)
                    ) { }
                    Card(
                        modifier = Modifier.width(300.dp).height(230.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkCardBackground),
                        elevation = CardDefaults.cardElevation(16.dp)
                    ) {

                    }
                }
            }
        }
    }
}