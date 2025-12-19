package com.danucdev.stocksystem.ui.screens.clients

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.danucdev.stocksystem.CardBackgroundFirst
import com.danucdev.stocksystem.CardBackgroundSecond
import com.danucdev.stocksystem.DarkFontColor
import com.danucdev.stocksystem.DarkMenuBackground
import com.danucdev.stocksystem.domain.models.ClientModel
import com.danucdev.stocksystem.ui.core.AcceptDeclineButtons
import com.danucdev.stocksystem.ui.core.ButtonTextItem
import com.danucdev.stocksystem.ui.core.CardBody
import com.danucdev.stocksystem.ui.core.CardTitle
import com.danucdev.stocksystem.ui.core.ScreenTitle
import com.danucdev.stocksystem.ui.core.TextFieldItem
import org.koin.compose.viewmodel.koinViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.ExperimentalTime

@Composable
fun ClientsScreen() {

    val viewmodel = koinViewModel<ClientsViewModel>()

    val clientName by viewmodel.clientName.collectAsState()
    val clientLastname by viewmodel.clientLastName.collectAsState()
    val phoneNumber by viewmodel.phoneNumber.collectAsState()
    val showAddClientDialog by viewmodel.showAddClientDialog.collectAsState()
    val clientsList by viewmodel.allClients.collectAsState()
    val birthDate by viewmodel.birthDate.collectAsState()

    var queryName by remember { mutableStateOf("") }

    var showBirthdayDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ScreenTitle("Clientes")
                ButtonTextItem("Agregar Cliente") { viewmodel.showAddClientDialog(true) }
            }
            Spacer(modifier = Modifier.size(0.dp))
            TextField(
                value = queryName,
                onValueChange = { input ->
                    val newInput = input.replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase() else char.toString()
                    }
                    queryName = newInput
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                shape = RoundedCornerShape(4.dp),
                trailingIcon = {
                    if (queryName.isNotBlank()) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "",
                            modifier = Modifier
                                .clickable {
                                    queryName = ""
                                }
                                .pointerHoverIcon(
                                    PointerIcon.Default
                                ),
                            tint = DarkFontColor
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = CardBackgroundSecond,
                    unfocusedContainerColor = CardBackgroundSecond,
                    focusedTextColor = DarkFontColor,
                    unfocusedTextColor = DarkFontColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,

                    ),
                maxLines = 1,
                singleLine = true,
                placeholder = { Text("Buscar por nombre") },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "",
                        tint = DarkFontColor
                    )
                })
            Spacer(modifier = Modifier.size(0.dp))
            Column(modifier = Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                clientsList.forEach { client ->
                    ClientItem(client)
                }
            }
            if (showAddClientDialog) {
                AddClientDialog(
                    viewmodel,
                    clientName,
                    clientLastname,
                    birthday = birthDate,
                    phoneNumber,
                    onBirthdayClicked = { showBirthdayDialog = true }
                ) { viewmodel.showAddClientDialog(false) }
            }

            BirthdayDatePicker(showBirthdayDialog, onDateSelected = { selected ->
                viewmodel.modifyBirthDate(selected)
            }) {
                showBirthdayDialog = false
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayDatePicker(
    showDialog: Boolean,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = java.time.Instant.now().toEpochMilli(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        })

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        val date = java.time.Instant.ofEpochMilli(millis)
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate()

                        onDateSelected(date)
                    }
                    onDismiss()
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}


@Composable
fun ClientItem(client: ClientModel) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackgroundSecond
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardBody(client.name)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CardBody(client.phone)
                Button(shape = CircleShape, onClick = { }, modifier = Modifier.size(35.dp), contentPadding = PaddingValues(8.dp), colors = ButtonDefaults.buttonColors(containerColor = DarkMenuBackground)) {
                    Icon(Icons.Filled.Edit, contentDescription = "", tint = DarkFontColor)
                }
                Button(shape = CircleShape, onClick = { }, modifier = Modifier.size(35.dp), contentPadding = PaddingValues(8.dp), colors = ButtonDefaults.buttonColors(containerColor = DarkMenuBackground)) {
                    Icon(Icons.Filled.Delete, contentDescription = "", tint = DarkFontColor)
                }
            }

        }
    }

}

@Composable
private fun AddClientDialog(
    viewmodel: ClientsViewModel,
    clientName: String,
    clientLastname: String,
    birthday:LocalDate?,
    phoneNumber: String,
    onBirthdayClicked: () -> Unit,
    onDismiss: () -> Unit,
) {

    val dateFormatter = DateTimeFormatter.ofPattern(
        "dd/MM/yyyy",
        Locale.getDefault()
    )

    Dialog(
        onDismissRequest = { onDismiss() },
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
                    CardTitle("Agregar Cliente")
                }
                Spacer(modifier = Modifier.size(0.dp))
                TextFieldItem(clientName, label = "Nombre", onClick = {}) { input ->
                    val newInput = input.replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase() else char.toString()
                    }
                    viewmodel.modifyClientName(newInput)
                }
                TextFieldItem(clientLastname, label = "Apellido", onClick = {}) { input ->
                    val newInput = input.replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase() else char.toString()
                    }
                    viewmodel.modifyClientLastName(newInput)
                }
                TextFieldItem(phoneNumber, label = "Teléfono", onClick = {}) { input ->
                    if (input.isBlank()) {
                        viewmodel.modifyPhoneNumber(input)
                    }
                    input.filter { it.isDigit() }
                    viewmodel.modifyPhoneNumber(input)
                }
                TextFieldItem(
                    birthday?.format(dateFormatter)?:"",
                    enabled = false,
                    label = "Fecha de nacimiento",
                    clickable = true,
                    onClick = { onBirthdayClicked() }
                ) { onBirthdayClicked() }
                Spacer(modifier = Modifier.size(0.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    AcceptDeclineButtons(
                        acceptButtonColor = Color.Green.copy(alpha = .6f),
                        onAcceptButtonClick = {
                            viewmodel.showAddClientDialog(false)
                            // Add Client if it is all right
                            viewmodel.addNewClient()
                            viewmodel.cleanDialogData()
                        },
                        onDeclineButtonClick = { viewmodel.showAddClientDialog(false) }
                    )
                }
            }
        }
    }
}


