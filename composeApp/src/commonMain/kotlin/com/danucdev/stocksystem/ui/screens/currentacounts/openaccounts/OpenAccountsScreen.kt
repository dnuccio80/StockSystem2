package com.danucdev.stocksystem.ui.screens.currentacounts.openaccounts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.danucdev.stocksystem.CardBackgroundSecond
import com.danucdev.stocksystem.DarkMenuBackground
import com.danucdev.stocksystem.domain.models.ClientModel
import com.danucdev.stocksystem.domain.models.CurrentAccountModel
import com.danucdev.stocksystem.ui.core.AcceptDeclineButtons
import com.danucdev.stocksystem.ui.core.CardBody
import com.danucdev.stocksystem.ui.core.CardTitle
import com.danucdev.stocksystem.ui.core.SearchBarItem
import com.danucdev.stocksystem.ui.core.TextFieldItem
import com.danucdev.stocksystem.ui.core.TitleAndButtonRowItemScreenWithSearchBar
import com.danucdev.stocksystem.ui.screens.currentacounts.details.CurrentAccountDetailsScreen
import org.koin.compose.viewmodel.koinViewModel

class OpenAccountsScreen : Screen {
    @Composable
    override fun Content() {
        val viewmodel = koinViewModel<OpenAccountsViewModel>()
        val navigator = LocalNavigator.currentOrThrow

        val query by viewmodel.querySearchCurrentAccount.collectAsState()
        val showAddCurrentAccountDialog by viewmodel.showAddCurrentAccountDialog.collectAsState()
        val showClientDropdownMenu by viewmodel.showClientDropdownMenu.collectAsState()
        val clientName by viewmodel.clientName.collectAsState()
        val clientsList by viewmodel.clientsList.collectAsState()
        val querySearchClient by viewmodel.querySearchClient.collectAsState()
        val currentAccountsList by viewmodel.currentAccountsList.collectAsState()
        val alreadyExistCurrentAccount by viewmodel.alreadyExist.collectAsState()

        val currentScreen = navigator.lastItem


        Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TitleAndButtonRowItemScreenWithSearchBar(
                    title = "Cuentas Corrientes",
                    buttonText = "Nueva cuenta corriente",
                    onButtonClick = { viewmodel.updateShowAddCurrentAccountDialog(true) },
                    query = query,
                    onSearchValueChange = { viewmodel.updateQuerySearchCurrentAccount(it) }
                )
                if (currentAccountsList.isNotEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        currentAccountsList.forEach { currentAccount ->
                            CurrentAccountItem(currentAccount) {
                                if (currentScreen !is CurrentAccountDetailsScreen) navigator.push(
                                    CurrentAccountDetailsScreen(currentAccount.id)
                                )
                            }
                        }
                    }
                } else {
                    Box(modifier = Modifier.padding(horizontal = 64.dp)) {
                        CardBody("No hay cuentas corrientes disponibles")
                    }
                }
            }
            if (showAddCurrentAccountDialog) {
                AddCurrentAccountDialog(
                    clientName = clientName,
                    clientsList = clientsList,
                    showClientDropdownMenu = showClientDropdownMenu,
                    querySearchClient = querySearchClient,
                    isAllData = viewmodel.isAllData(),
                    isAlreadyCurrentAccount = alreadyExistCurrentAccount,
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

                            OpenAccountsActions.ADD_NEW_CURRENT_ACCOUNT -> {
                                viewmodel.tryAddCurrentAccount()
                            }

                            OpenAccountsActions.DISMISS -> {
                                viewmodel.updateShowAddCurrentAccountDialog(false)
                                viewmodel.cleanData()
                            }

                            OpenAccountsActions.CLOSE_CLIENT_LIST -> {
                                viewmodel.updateShowClientDropdownMenu(
                                    false
                                )
                                viewmodel.updateQueryClientName("")
                            }

                            OpenAccountsActions.CHANGE_QUERY -> viewmodel.updateQueryClientName(
                                value
                            )
                        }
                    },
                )
            }
        }

    }
}

@Composable
private fun CurrentAccountItem(currentAccount: CurrentAccountModel, onAccountClicked: () -> Unit) {

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp)
            .clickable { onAccountClicked() }.pointerHoverIcon(
                PointerIcon.Hand
            ),
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
            CardBody(currentAccount.clientName)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CardBody("Deuda total: $${currentAccount.amount}")
            }
        }
    }
}


enum class OpenAccountsActions {
    OPEN_CLIENT_LIST, CLOSE_CLIENT_LIST, CLIENT_SELECTED, ADD_NEW_CURRENT_ACCOUNT, DISMISS, CHANGE_QUERY
}

@Composable
private fun AddCurrentAccountDialog(
    clientName: String,
    clientsList: List<ClientModel>,
    showClientDropdownMenu: Boolean,
    querySearchClient: String,
    isAllData: Boolean,
    isAlreadyCurrentAccount: Boolean?,
    onActionDone: (OpenAccountsActions, String) -> Unit,
) {
    var showError by remember { mutableStateOf(false) }
    var showAlreadyExistMessage by remember { mutableStateOf(false) }

    LaunchedEffect(isAlreadyCurrentAccount) {
        when (isAlreadyCurrentAccount) {
            true -> showAlreadyExistMessage = true
            false -> {
                onActionDone(OpenAccountsActions.DISMISS, "")
                showAlreadyExistMessage = false
            }

            null -> {}
        }
    }

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
                        trailingIcon = if (showClientDropdownMenu) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
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
                        onQueryChange = { query ->
                            onActionDone(OpenAccountsActions.CHANGE_QUERY, query)
                        }

                    )
                }
                if (isAlreadyCurrentAccount != null) {
                    androidx.compose.animation.AnimatedVisibility(isAlreadyCurrentAccount) {
                        Text(
                            "Ya existe una cuenta corriente de este cliente",
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
                AnimatedVisibility(visible = showError) {
                    Text("Faltan rellenar datos", color = Color.Red, fontSize = 12.sp)
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

            }
        }
    }
}

@Composable
fun ClientDropdownMenuItem(
    list: List<ClientModel>,
    show: Boolean,
    querySearchClient: String,
    onDismiss: () -> Unit,
    onClientSelected: (String) -> Unit,
    onQueryChange: (String) -> Unit,
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
            SearchBarItem(
                querySearchClient,
                onValueChange = { onQueryChange(it) }
            )
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth().background(CardBackgroundSecond),
                text = { CardBody("No hay clientes añadidos") },
                onClick = { onDismiss() }
            )
        }

    }
}

