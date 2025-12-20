package com.danucdev.stocksystem.ui.screens.concessions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.danucdev.stocksystem.DarkMenuBackground
import com.danucdev.stocksystem.ui.core.AcceptDeclineButtons
import com.danucdev.stocksystem.ui.core.ButtonTextItem
import com.danucdev.stocksystem.ui.core.CardBody
import com.danucdev.stocksystem.ui.core.CardTitle
import com.danucdev.stocksystem.ui.core.ScreenTitle
import com.danucdev.stocksystem.ui.core.TextFieldItem

@Composable
fun ConcessionScreen() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ScreenTitle("Inventario")
            ButtonTextItem("Agregar item") {}
        }
        AddConcessionDialog()
    }
}

@Composable
fun AddConcessionDialog() {

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var currentStock by remember { mutableStateOf("") }
    var adviceStock by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    var questionClick by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Dialog(
        onDismissRequest = { },
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = DarkMenuBackground
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
                    CardTitle(if (true) "Agregar Artículo" else "Editar Artículo")
                }
                Spacer(modifier = Modifier.size(0.dp))
                TextFieldItem(
                    name,
                    label = "Nombre del articulo",
                    focusRequester = focusRequester,
                    onClick = {}) { input ->
                    val newInput = input.replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase() else char.toString()
                    }
                    name = newInput
                }
                TextFieldItem(price, label = "Precio", onClick = {}) { input ->
                    if (input.isBlank()) {
                        price = ""
                    }
                    val newInput = input.filter { it.isDigit() }
                    price = newInput
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked, onCheckedChange = { checked = !checked })
                    CardBody("Gestionar stock")
                }
                androidx.compose.animation.AnimatedVisibility(checked) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        TextFieldItem(currentStock, label = "Stock actual", onClick = {}) { input ->
                            if (input.isBlank()) {
                                currentStock = ""
                            }
                            val newInput = input.filter { it.isDigit() }
                            currentStock = newInput
                        }
                        TextFieldItem(
                            adviceStock,
                            label = "Aviso de pocas existencias",
                            onClick = {}) { input ->
                            if (input.isBlank()) {
                                adviceStock = ""
                            }
                            val newInput = input.filter { it.isDigit() }
                            adviceStock = newInput
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Card(
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Crossfade(questionClick) { isClicked ->
                                    Icon(
                                        if (!isClicked) Icons.Filled.QuestionMark else Icons.Filled.Close,
                                        contentDescription = "",
                                        tint = Color.Black,
                                        modifier = Modifier.padding(2.dp)
                                            .clickable { questionClick = !questionClick })
                                }
                            }
                            androidx.compose.animation.AnimatedVisibility(questionClick) {
                                CardBody("Se mostrará un mensaje de alerta de poco stock en el panel principal y en el panel de inventario cuando la cantidad de articulos sea igual o menor que el número que insertes en aviso de pocas existencias")
                            }
                        }

                    }
                }
                Spacer(modifier = Modifier.size(0.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    AcceptDeclineButtons(
                        acceptButtonColor = Color.Green.copy(alpha = .6f),
                        onAcceptButtonClick = { },
                        onDeclineButtonClick = { }
                    )
                }
                AnimatedVisibility(visible = true) {
                    Text("Faltan rellenar datos", color = Color.Red, fontSize = 12.sp)
                }
            }
        }
    }
}