package com.danucdev.stocksystem.ui.screens.clients

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.danucdev.stocksystem.CardBackgroundSecond
import com.danucdev.stocksystem.DarkFontColor
import com.danucdev.stocksystem.DarkMenuBackground
import com.danucdev.stocksystem.domain.models.ClientModel
import com.danucdev.stocksystem.ui.core.AcceptDeclineButtons
import com.danucdev.stocksystem.ui.core.ButtonTextItem
import com.danucdev.stocksystem.ui.core.CardBody
import com.danucdev.stocksystem.ui.core.CardTitle
import com.danucdev.stocksystem.ui.core.ConfirmDialog
import com.danucdev.stocksystem.ui.core.ScreenTitle
import com.danucdev.stocksystem.ui.core.SearchBarItem
import com.danucdev.stocksystem.ui.core.TextFieldItem
import com.danucdev.stocksystem.ui.core.TitleAndButtonRowItemScreen
import com.danucdev.stocksystem.ui.core.TitleAndButtonRowItemScreenWithSearchBar
import org.koin.compose.viewmodel.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ClientsScreen() {

    val viewmodel = koinViewModel<ClientsViewModel>()

    val clientName by viewmodel.clientName.collectAsState()
    val clientLastname by viewmodel.clientLastName.collectAsState()
    val phoneNumber by viewmodel.phoneNumber.collectAsState()
    val showAddClientDialog by viewmodel.showAddClientDialog.collectAsState()
    val showEditClientDialog by viewmodel.showEditClientDialog.collectAsState()
    val clientsList by viewmodel.allClients.collectAsState()
    val birthDate by viewmodel.birthDate.collectAsState()
    val queryClientName by viewmodel.queryClientName.collectAsState()
    val showBirthdayDialog by viewmodel.showBirthdayDialog.collectAsState()


    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TitleAndButtonRowItemScreenWithSearchBar(
                title = "Clientes",
                buttonText = "Agregar Cliente",
                onButtonClick = { viewmodel.showAddClientDialog(true) },
                query = queryClientName,
                onSearchValueChange = { viewmodel.updateQueryClientName(it) }
            )
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (clientsList.isNotEmpty()) {
                    clientsList.forEach { client ->
                        ClientItem(
                            client = client,
                            onDeleteClient = { viewmodel.deleteClientData(client.id) },
                            onModifyClient = {
                                viewmodel.assignClientData(client)
                                viewmodel.showEditClientDialog(true)
                            }
                        )
                    }
                } else {
                    Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp)) {
                        CardBody("No hay clientes para mostrar")
                    }
                }
            }
            // Add new client
            if (showAddClientDialog) {
                ClientDialog(
                    clientName = clientName,
                    editDialog = false,
                    clientLastname = clientLastname,
                    birthday = birthDate,
                    phoneNumber = phoneNumber,
                    isAllData = viewmodel.isAllData(),
                    onBirthdayClicked = { viewmodel.updateShowBirthdayDialog(true) },
                    onActionDone = { action, value ->
                        when (action) {
                            ClientDataActions.MODIFY_NAME -> viewmodel.modifyClientName(value)
                            ClientDataActions.MODIFY_LASTNAME -> viewmodel.modifyClientLastName(
                                value
                            )

                            ClientDataActions.MODIFY_PHONE -> viewmodel.modifyPhoneNumber(value)
                            ClientDataActions.DISMISS -> viewmodel.showAddClientDialog(false)
                            ClientDataActions.ADD_CLIENT -> {
                                viewmodel.showAddClientDialog(false)
                                viewmodel.addNewClient()
                                viewmodel.cleanDialogData()
                            }

                            ClientDataActions.UPDATE_DATA -> {}
                        }
                    }
                )
            }
            // Edit Client
            if (showEditClientDialog) {
                ClientDialog(
                    clientName = clientName,
                    editDialog = true,
                    clientLastname = clientLastname,
                    birthday = birthDate,
                    phoneNumber = phoneNumber,
                    isAllData = viewmodel.isAllData(),
                    onBirthdayClicked = { viewmodel.updateShowBirthdayDialog(true) },
                    onActionDone = { action, value ->
                        when (action) {
                            ClientDataActions.MODIFY_NAME -> viewmodel.modifyClientName(value)
                            ClientDataActions.MODIFY_LASTNAME -> viewmodel.modifyClientLastName(
                                value
                            )

                            ClientDataActions.MODIFY_PHONE -> viewmodel.modifyPhoneNumber(value)
                            ClientDataActions.DISMISS -> {
                                viewmodel.showEditClientDialog(false)
                                viewmodel.cleanDialogData()
                            }

                            ClientDataActions.ADD_CLIENT -> {}
                            ClientDataActions.UPDATE_DATA -> {
                                viewmodel.showEditClientDialog(false)
                                viewmodel.updateClient()
                                viewmodel.cleanDialogData()
                            }
                        }
                    })
            }

            BirthdayDatePicker(showBirthdayDialog, onDateSelected = { selected ->
                viewmodel.modifyBirthDate(selected)
            }) {
                viewmodel.updateShowBirthdayDialog(false)
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
        initialSelectedDateMillis = Instant.now().toEpochMilli(),
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
                        val date = Instant.ofEpochMilli(millis)
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
private fun ClientItem(
    client: ClientModel,
    onDeleteClient: () -> Unit,
    onModifyClient: () -> Unit,
) {

    var showConfirmDeleteClientDialog by remember { mutableStateOf(false) }

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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CardBody("id: ${client.id}")
                CardBody(client.phone)
                Button(
                    shape = CircleShape,
                    onClick = { onModifyClient() },
                    modifier = Modifier.size(35.dp),
                    contentPadding = PaddingValues(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkMenuBackground)
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "", tint = DarkFontColor)
                }
                Button(
                    shape = CircleShape,
                    onClick = { showConfirmDeleteClientDialog = true },
                    modifier = Modifier.size(35.dp),
                    contentPadding = PaddingValues(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkMenuBackground)
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "", tint = DarkFontColor)
                }
            }

        }
        if (showConfirmDeleteClientDialog) {
            ConfirmDialog(
                "¿Seguro que querés eliminar a este cliente?",
                onConfirm = {
                    onDeleteClient()
                    showConfirmDeleteClientDialog = false
                },
                onDismiss = { showConfirmDeleteClientDialog = false })
        }

    }
}

enum class ClientDataActions {
    MODIFY_NAME, MODIFY_LASTNAME, MODIFY_PHONE, DISMISS, ADD_CLIENT, UPDATE_DATA
}

@Composable
private fun ClientDialog(
    clientName: String,
    editDialog: Boolean,
    clientLastname: String,
    birthday: LocalDate?,
    phoneNumber: String,
    isAllData: Boolean,
    onBirthdayClicked: () -> Unit,
    onActionDone: (ClientDataActions, String) -> Unit,
) {

    val dateFormatter = DateTimeFormatter.ofPattern(
        "dd/MM/yyyy",
        Locale.getDefault()
    )
    var showError by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Dialog(
        onDismissRequest = { onActionDone(ClientDataActions.DISMISS, "") },
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
                    CardTitle(if (editDialog) "Editar Cliente" else "Agregar Cliente")
                }
                Spacer(modifier = Modifier.size(0.dp))
                TextFieldItem(
                    clientName,
                    label = "Nombre",
                    focusRequester = focusRequester,
                    onClick = {}) { input ->
                    val newInput = input.replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase() else char.toString()
                    }
                    onActionDone(ClientDataActions.MODIFY_NAME, newInput)
                }
                TextFieldItem(clientLastname, label = "Apellido", onClick = {}) { input ->
                    val newInput = input.replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase() else char.toString()
                    }
                    onActionDone(ClientDataActions.MODIFY_LASTNAME, newInput)
                }
                TextFieldItem(phoneNumber, label = "Teléfono", onClick = {}) { input ->
                    if (input.isBlank()) {
                        onActionDone(ClientDataActions.MODIFY_PHONE, input)
                    }
                    val newInput = input.filter { it.isDigit() }
                    onActionDone(ClientDataActions.MODIFY_PHONE, newInput)
                }
                TextFieldItem(
                    birthday?.format(dateFormatter) ?: "",
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
                            if (isAllData) {
                                showError = false
                                if (editDialog) {
                                    onActionDone(ClientDataActions.UPDATE_DATA, "")
                                } else {
                                    onActionDone(ClientDataActions.ADD_CLIENT, "")
                                }
                            } else {
                                showError = true
                            }
                        },
                        onDeclineButtonClick = { onActionDone(ClientDataActions.DISMISS, "") }
                    )
                }
                AnimatedVisibility(visible = showError) {
                    Text("Faltan rellenar datos", color = Color.Red, fontSize = 12.sp)
                }
            }
        }
    }
}


