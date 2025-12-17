package com.danucdev.stocksystem.ui.screens.clients

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.danucdev.stocksystem.CardBackgroundFirst
import com.danucdev.stocksystem.CardBackgroundSecond
import com.danucdev.stocksystem.DarkFontColor
import com.danucdev.stocksystem.DarkMenuBackground
import com.danucdev.stocksystem.ui.core.ButtonTextItem
import com.danucdev.stocksystem.ui.core.CardBody
import com.danucdev.stocksystem.ui.core.CardTitle
import com.danucdev.stocksystem.ui.core.ScreenTitle
import com.danucdev.stocksystem.ui.core.TextFieldItem

@Composable
fun ClientsScreen() {

    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ScreenTitle("Clientes")
            ButtonTextItem("Agregar Cliente") { showDialog = true }
            if (!showDialog) return
            Dialog(
                onDismissRequest = { showDialog = false },
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CardBackgroundFirst
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CardTitle("Agregar Cliente")
                        }
                        Spacer(modifier = Modifier.size(0.dp))
                        TextFieldItem("", label = "Nombre") { }
                        TextFieldItem("", label = "Apellido") { }
                        TextFieldItem("", enabled = false, label = "Fecha de nacimiento") { }
                        Spacer(modifier = Modifier.size(0.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            AcceptDeclineButtons(
                                acceptButtonColor = Color.Green.copy(alpha = .6f),
                                onAcceptButtonClick = {
                                    showDialog = false
                                    // Add Client if it is all right

                                },
                                onDeclineButtonClick = { showDialog = false }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AcceptDeclineButtons(
    acceptText: String = "Aceptar",
    declineText: String = "Cancelar",
    acceptButtonColor: Color = CardBackgroundSecond,
    declineButtonColor: Color = CardBackgroundSecond,
    onAcceptButtonClick: () -> Unit,
    onDeclineButtonClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ButtonTextItem(acceptText, acceptButtonColor) { onAcceptButtonClick() }
        ButtonTextItem(declineText, declineButtonColor) { onDeclineButtonClick() }
    }
}