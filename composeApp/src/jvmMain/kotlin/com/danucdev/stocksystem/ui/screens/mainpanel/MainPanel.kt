package com.danucdev.stocksystem.ui.screens.mainpanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danucdev.stocksystem.DarkAccentColorText
import com.danucdev.stocksystem.DarkFontColor
import com.danucdev.stocksystem.DarkMenuBackground
import com.danucdev.stocksystem.ui.core.AccentText
import com.danucdev.stocksystem.ui.core.ButtonTextItem
import com.danucdev.stocksystem.ui.core.CardBody
import com.danucdev.stocksystem.ui.core.CardTitle
import com.danucdev.stocksystem.ui.core.ScreenTitle

@Composable
fun MainPanelScreen() {

    val verticalScroll = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.verticalScroll(verticalScroll)) {
            MainHeader()
            FloatingCardItem("Balance") {
                AccentText("$32.500")
                RowCardItem("Efectivo", "$50.000")
                RowCardItem("Transferencias", "$115.250")
                RowCardItem("Cuentas corrientes", "\$3.500")
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    ButtonTextItem("Ver Resumen") {  }
                }
            }
            FloatingCardItem("Turnos libres del día") {
                RowCardItem("Libres en cancha 1", "3 turnos")
                RowCardItem("Libres en cancha 2", "5 turnos")
            }
            FloatingCardItem("Cuentas Corrientes") {
                AccentText("$580.000", color = DarkAccentColorText)
                RowCardItem("Cuentas corrientes activas:", "33 cuentas")
            }
        }
    }
}

@Composable
private fun FloatingCardItem(title:String, content:@Composable () -> Unit) {
    Card(
        modifier = Modifier.width(400.dp),
        backgroundColor = DarkMenuBackground,
        shape = RoundedCornerShape(4.dp),
        elevation = 16.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                CardTitle(title)
                Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(Color.White))
            }
            Spacer(modifier = Modifier.size(0.dp))
            content()
        }
    }
}


@Composable
private fun MainHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ScreenTitle("Panel Principal")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ButtonTextItem("Abrir Caja") { }
            ButtonTextItem("Cerrar Caja") { }
        }
    }
}

@Composable
private fun RowCardItem(title:String, body:String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CardBody(title)
        Spacer(modifier = Modifier.weight(1f))
        CardBody(body)
    }
}



