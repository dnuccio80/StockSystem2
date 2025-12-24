package com.danucdev.stocksystem.ui.screens.currentacounts.openaccounts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.danucdev.stocksystem.CardBackgroundSecond
import com.danucdev.stocksystem.DarkMenuBackground
import com.danucdev.stocksystem.domain.models.ClientModel
import com.danucdev.stocksystem.ui.core.AcceptDeclineButtons
import com.danucdev.stocksystem.ui.core.CardBody
import com.danucdev.stocksystem.ui.core.CardTitle
import com.danucdev.stocksystem.ui.core.SearchBarItem
import com.danucdev.stocksystem.ui.core.TextFieldItem
import com.danucdev.stocksystem.ui.core.TitleAndButtonRowItemScreenWithSearchBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OpenAccountsScreen() {

    val viewmodel = koinViewModel<OpenAccountsViewModel>()

    val query by viewmodel.querySearchCurrentAccount.collectAsState()
    val showAddCurrentAccountDialog by viewmodel.showAddCurrentAccountDialog.collectAsState()
    val showClientDropdownMenu by viewmodel.showClientDropdownMenu.collectAsState()
    val clientName by viewmodel.clientName.collectAsState()
    val clientsList by viewmodel.clientsList.collectAsState()
    val querySearchClient by viewmodel.querySearchClient.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TitleAndButtonRowItemScreenWithSearchBar(
                title = "Cuentas Corrientes",
                buttonText = "Nueva cuenta corriente",
                onButtonClick = { viewmodel.updateShowAddAccount(true) },
                query = query,
                onSearchValueChange = { viewmodel.updateQuerySearchCurrentAccount(it) }
            )
        }
        if (showAddCurrentAccountDialog) {
            AddCurrentAccountDialog(
                clientName = clientName,
                clientsList = clientsList,
                showClientDropdownMenu = showClientDropdownMenu,
                querySearchClient = querySearchClient,
                isAllData = viewmodel.isAllData(),
                onActionDone = { action, value ->
                    when (action) {
                        OpenAccountsActions.OPEN_CLIENT_LIST -> viewmodel.updateShowClientDropdownMenu(
                            true
                        )

                        OpenAccountsActions.CLIENT_SELECTED -> {
                            viewmodel.updateShowClientDropdownMenu(false)
                            viewmodel.updateClientSelected(value)
                            viewmodel.updateQueryClientName("")
                        }

                        OpenAccountsActions.ADD_NEW_CLIENT -> { /* Mostrar un dialog para agregar un cliente, como en add client screen */
                        }

                        OpenAccountsActions.ADD_NEW_CURRENT_ACCOUNT -> viewmodel.addNewCurrentAccount()
                        OpenAccountsActions.DISMISS -> {
                            viewmodel.updateShowAddAccount(false)
                            viewmodel.updateClientSelected("")
                            viewmodel.updateQueryClientName("")
                        }
                        OpenAccountsActions.CLOSE_CLIENT_LIST -> {
                            viewmodel.updateShowClientDropdownMenu(
                                false
                            )
                            viewmodel.updateQueryClientName("")
                        }
                        OpenAccountsActions.CHANGE_QUERY -> viewmodel.updateQueryClientName(value)
                    }
                },
            )
        }
    }
}

enum class OpenAccountsActions {
    OPEN_CLIENT_LIST, CLOSE_CLIENT_LIST, CLIENT_SELECTED, ADD_NEW_CLIENT, ADD_NEW_CURRENT_ACCOUNT, DISMISS, CHANGE_QUERY
}

@Composable
private fun AddCurrentAccountDialog(
    clientName: String,
    clientsList: List<ClientModel>,
    showClientDropdownMenu: Boolean,
    querySearchClient:String,
    isAllData: Boolean,
    onActionDone: (OpenAccountsActions, String) -> Unit,
) {
    var showError by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { onActionDone(OpenAccountsActions.DISMISS, "") },
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
                    CardTitle("Nueva cuenta corriente")
                }
                Spacer(modifier = Modifier.size(0.dp))
                Column {
                    TextFieldItem(
                        clientName,
                        enabled = false,
                        label = "Cliente",
                        trailingIcon = if(showClientDropdownMenu) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        clickable = true,
                        onClick = { onActionDone(OpenAccountsActions.OPEN_CLIENT_LIST, "") }
                    ) { }
                    ClientDropdownMenuItem(
                        list = clientsList,
                        show = showClientDropdownMenu,
                        querySearchClient = querySearchClient,
                        onDismiss = { onActionDone(OpenAccountsActions.CLOSE_CLIENT_LIST, "") },
                        onClientSelected = { clientName ->
                            onActionDone(
                                OpenAccountsActions.CLIENT_SELECTED,
                                clientName
                            )
                        },
                        onQueryChange = {query ->
                            onActionDone(OpenAccountsActions.CHANGE_QUERY, query)
                        }

                    )
                }

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
                                onActionDone(OpenAccountsActions.ADD_NEW_CURRENT_ACCOUNT, "")
                            } else {
                                showError = true
                            }
                        },
                        onDeclineButtonClick = { onActionDone(OpenAccountsActions.DISMISS, "") }
                    )
                }
                AnimatedVisibility(visible = showError) {
                    Text("Faltan rellenar datos", color = Color.Red, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun ClientDropdownMenuItem(
    list: List<ClientModel>,
    show: Boolean,
    querySearchClient:String,
    onDismiss: () -> Unit,
    onClientSelected: (String) -> Unit,
    onQueryChange:(String) -> Unit
) {
    DropdownMenu(
        expanded = show,
        containerColor = CardBackgroundSecond,
        modifier = Modifier.heightIn(min = 50.dp, max = 250.dp).widthIn(min = 250.dp),
        scrollState = rememberScrollState(),
        onDismissRequest = { onDismiss() }
    ) {
        if (list.isNotEmpty()) {
            SearchBarItem(
                querySearchClient,
                onValueChange = { onQueryChange(it) }
            )
            list.forEach { client ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxSize().background(CardBackgroundSecond),
                    text = { CardBody(client.name) },
                    onClick = { onClientSelected(client.name) }
                )
            }
        } else {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth().background(CardBackgroundSecond),
                text = { CardBody("No hay clientes añadidos") },
                onClick = { onDismiss() }
            )
        }

    }
}

