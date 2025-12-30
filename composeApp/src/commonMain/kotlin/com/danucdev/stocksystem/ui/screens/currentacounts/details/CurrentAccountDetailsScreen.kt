package com.danucdev.stocksystem.ui.screens.currentacounts.details

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.danucdev.stocksystem.CardBackgroundSecond
import com.danucdev.stocksystem.DarkAccentColorText
import com.danucdev.stocksystem.DarkMenuBackground
import com.danucdev.stocksystem.PositiveColor
import com.danucdev.stocksystem.domain.models.CurrentAccountModel
import com.danucdev.stocksystem.domain.models.TransactionModel
import com.danucdev.stocksystem.ui.core.AcceptDeclineButtons
import com.danucdev.stocksystem.ui.core.ButtonTextItem
import com.danucdev.stocksystem.ui.core.CardBody
import com.danucdev.stocksystem.ui.core.CardTitle
import com.danucdev.stocksystem.ui.core.ConfirmDialog
import com.danucdev.stocksystem.ui.core.MoneyTextField
import com.danucdev.stocksystem.ui.core.ScreenTitle
import com.danucdev.stocksystem.ui.core.TextFieldItem
import com.danucdev.stocksystem.ui.screens.core.PaymentMethods
import com.danucdev.stocksystem.ui.screens.helpers.DateUtils
import com.danucdev.stocksystem.ui.screens.helpers.NumberUtils
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.abs

class CurrentAccountDetailsScreen(clientId: Int) : Screen {

    private val clientRef = clientId

    @Composable
    override fun Content() {
        val viewmodel = koinViewModel<CurrentAccountsDetailsViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val clientDetails by viewmodel.accountDetails.collectAsState()
        val isLoading by viewmodel.isLoading.collectAsState()
        val transactions by viewmodel.transactions.collectAsState()
        val totalAmount by viewmodel.totalAmount.collectAsState()
        val showConfirmDialog by viewmodel.showConfirmDialog.collectAsState()
        val showAddPaymentDialog by viewmodel.showAddPaymentDialog.collectAsState()
        val paymentAmount by viewmodel.paymentAmount.collectAsState()
        val paymentMethod by viewmodel.paymentMethod.collectAsState()
        val showPaymentMethodSelector by viewmodel.showPaymentMethodSelector.collectAsState()

        LaunchedEffect(true) {
            viewmodel.getDetails(clientRef)
        }

        Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                    return@Box
                }
                Header(
                    clientDetails!!,
                    navigator,
                    totalAmount
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ButtonTextItem("Añadir deuda") { viewmodel.addDebt() }
                        ButtonTextItem("Realizar un pago") {
                            viewmodel.modifyShowAddPaymentDialog(
                                true
                            )
                        }
                        ButtonTextItem("Limpiar todo el registro") {
                            viewmodel.modifyShowConfirmDialog(
                                true
                            )
                        }
                    }
                }
                if (transactions?.isEmpty() == true) CardBody("No hay registro de transacciones")
                else {
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        transactions?.forEach { transaction ->
                            TransactionDetailsItem(transaction)
                        }
                    }
                }
                if (showConfirmDialog) {
                    ConfirmDialog(
                        "¿Estás seguro de eliminar todo el registro? Eliminarás todo el historial de deudas y pagos del cliente",
                        onConfirm = {
                            viewmodel.modifyShowConfirmDialog(false)
                            viewmodel.deleteAllTransactions()
                        },
                        onDismiss = { viewmodel.modifyShowConfirmDialog(false) },
                    )
                }
                if (showAddPaymentDialog) {
                    AddPaymentDialog(
                        amount = paymentAmount,
                        paymentMethod = paymentMethod?.method ?: "",
                        showPaymentMethodSelector = showPaymentMethodSelector,
                        isAllData = viewmodel.isAllPaymentData(),
                        onActionsDone = { action, value ->
                            when (action) {
                                DetailsScreenActions.CHANGE_AMOUNT -> viewmodel.modifyAmountPayment(
                                    value
                                )

                                DetailsScreenActions.CHANGE_PAYMENT_METHOD -> {
                                    val method = if(value == PaymentMethods.Cash.method) {
                                        PaymentMethods.Cash
                                    } else PaymentMethods.MoneyTransfer
                                    viewmodel.modifyPaymentMethod(method)
                                    viewmodel.modifyShowPaymentMethodSelector(false)
                                }

                                DetailsScreenActions.DISMISS -> viewmodel.modifyShowAddPaymentDialog(
                                    false
                                )

                                DetailsScreenActions.CANCEL -> {
                                    viewmodel.modifyShowAddPaymentDialog(false)
                                    viewmodel.cleanPaymentData()
                                }

                                DetailsScreenActions.OPEN_PAYMENT_METHOD_SELECTOR -> viewmodel.modifyShowPaymentMethodSelector(
                                    true
                                )

                                DetailsScreenActions.CLOSE_PAYMENT_METHOD_SELECTOR -> viewmodel.modifyShowPaymentMethodSelector(
                                    false
                                )

                                DetailsScreenActions.PAYMENT_DONE -> {
                                    viewmodel.addPayment()
                                    viewmodel.modifyShowAddPaymentDialog(false)
                                    viewmodel.cleanPaymentData()
                                }
                            }
                        },
                    )
                }
            }
        }
    }
}

enum class DetailsScreenActions {
    CHANGE_AMOUNT, CHANGE_PAYMENT_METHOD, DISMISS, CANCEL, OPEN_PAYMENT_METHOD_SELECTOR, CLOSE_PAYMENT_METHOD_SELECTOR, PAYMENT_DONE
}

@Composable
private fun AddPaymentDialog(
    amount: String,
    paymentMethod: String,
    showPaymentMethodSelector: Boolean,
    isAllData: Boolean,
    onActionsDone: (DetailsScreenActions, String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    var showError by remember { mutableStateOf(false) }

    val paymentMethods = listOf(
        PaymentMethods.Cash.method,
        PaymentMethods.MoneyTransfer.method
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Dialog(
        onDismissRequest = { onActionsDone(DetailsScreenActions.DISMISS, "") },
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
                    CardTitle("Agregar un pago")
                }
                Spacer(modifier = Modifier.size(0.dp))
                MoneyTextField(
                    amount = amount.toLongOrNull() ?: 0,
                    label = "Monto",
                    onAmountChange = {
                        onActionsDone(
                            DetailsScreenActions.CHANGE_AMOUNT,
                            it.toString()
                        )
                    },
                    focusRequester = focusRequester,
                    enabled = true
                )
                Column {
                    TextFieldItem(
                        paymentMethod,
                        enabled = false,
                        label = "Método de pago",
                        clickable = true,
                        trailingIcon = Icons.Filled.KeyboardArrowDown,
                        onClick = {
                            onActionsDone(
                                DetailsScreenActions.OPEN_PAYMENT_METHOD_SELECTOR,
                                ""
                            )
                        }
                    ) { }
                    TransactionDropdownMenuItem(
                        paymentMethods,
                        show = showPaymentMethodSelector,
                        onDismiss = {
                            onActionsDone(
                                DetailsScreenActions.CLOSE_PAYMENT_METHOD_SELECTOR,
                                ""
                            )
                        },
                        onPaymentMethodChosen = { payment ->
                            onActionsDone(DetailsScreenActions.CHANGE_PAYMENT_METHOD, payment)
                        },
                    )
                }
                AnimatedVisibility(showError) {
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
                                onActionsDone(DetailsScreenActions.PAYMENT_DONE, "")
                            } else {
                                showError = true
                            }
                        },
                        onDeclineButtonClick = { onActionsDone(DetailsScreenActions.CANCEL, "") }
                    )
                }

            }
        }
    }
}

@Composable
private fun TransactionDropdownMenuItem(
    list: List<String>,
    show: Boolean,
    onDismiss: () -> Unit,
    onPaymentMethodChosen: (String) -> Unit,
) {
    DropdownMenu(
        expanded = show,
        containerColor = CardBackgroundSecond,
        modifier = Modifier.heightIn(min = 50.dp, max = 250.dp).widthIn(min = 250.dp),
        scrollState = rememberScrollState(),
        onDismissRequest = { onDismiss() }
    ) {
        list.forEach { payment ->
            DropdownMenuItem(
                modifier = Modifier.fillMaxSize().background(CardBackgroundSecond),
                text = { CardBody(payment) },
                onClick = { onPaymentMethodChosen(payment) }
            )
        }
    }
}

@Composable
private fun TransactionDetailsItem(transaction: TransactionModel) {

    val isPayment = NumberUtils.isNegativeNumber(transaction.amount.toLong())

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp)
            .clickable { }.pointerHoverIcon(
                PointerIcon.Hand
            ),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if(isPayment) PositiveColor else CardBackgroundSecond
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardBody(transaction.details)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                CardBody(NumberUtils.formatPriceNumberWithDollarSign(abs(transaction.amount.toLong())))
                CardBody(DateUtils.dateFormatter(transaction.date))
            }
        }
    }
}

@Composable
private fun Header(
    client: CurrentAccountModel,
    navigator: Navigator,
    totalAmount: Long,
) {

    val positiveAmount = NumberUtils.isNegativeNumber(totalAmount)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                "",
                modifier = Modifier.clickable { navigator.pop() },
                tint = Color.White
            )
            ScreenTitle("Cuenta corriente: ${client.clientName}")
        }
        if(positiveAmount) {
            val absAmount = abs(totalAmount)
            ScreenTitle(
                "Saldo a favor: ${NumberUtils.formatPriceNumberWithDollarSign(absAmount)}",
                color = PositiveColor
            )
        } else {
            ScreenTitle(
                "Deuda total: ${NumberUtils.formatPriceNumberWithDollarSign(totalAmount)}",
                color = DarkAccentColorText
            )
        }

    }
}