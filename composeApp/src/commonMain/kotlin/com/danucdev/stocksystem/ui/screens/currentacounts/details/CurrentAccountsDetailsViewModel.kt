package com.danucdev.stocksystem.ui.screens.currentacounts.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.stocksystem.domain.models.CurrentAccountModel
import com.danucdev.stocksystem.domain.models.TransactionModel
import com.danucdev.stocksystem.domain.usecases.currentaccounts.GetCurrentAccountDetails
import com.danucdev.stocksystem.domain.usecases.transactions.AddTransaction
import com.danucdev.stocksystem.domain.usecases.transactions.DeleteAllTransactionsByClientId
import com.danucdev.stocksystem.domain.usecases.transactions.DeleteTransaction
import com.danucdev.stocksystem.domain.usecases.transactions.GetTransactionsByClientId
import com.danucdev.stocksystem.domain.usecases.transactions.UpdateTransaction
import com.danucdev.stocksystem.ui.screens.core.PaymentMethods
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class CurrentAccountsDetailsViewModel(
    private val getCurrentAccountDetails: GetCurrentAccountDetails,
    getTransactionsByClientId: GetTransactionsByClientId,
    private val addTransaction: AddTransaction,
    private val deleteTransaction: DeleteTransaction,
    private val updateTransaction: UpdateTransaction,
    private val deleteAllTransactionsByClientId: DeleteAllTransactionsByClientId,
) : ViewModel() {

    private val _transactionDataToModification = MutableStateFlow<TransactionModel?>(null)

    private val _isEditableDebt = MutableStateFlow(false)
    val isEditableDebt: StateFlow<Boolean> = _isEditableDebt

    private val _isEditablePayment = MutableStateFlow(false)
    val isEditablePayment: StateFlow<Boolean> = _isEditablePayment

    private val _accountDetails = MutableStateFlow<CurrentAccountModel?>(null)
    val accountDetails: StateFlow<CurrentAccountModel?> = _accountDetails

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _transactions: StateFlow<List<TransactionModel>?> =
        accountDetails.filterNotNull().flatMapLatest { account ->
            getTransactionsByClientId(account.id)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val transactions = _transactions

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _totalAmount: StateFlow<Long> = transactions.filterNotNull().map { list ->
        list.sumOf { transaction ->
            transaction.amount.toLong()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)
    val totalAmount = _totalAmount

    private val _showConfirmDeleteAllRegistryDialog = MutableStateFlow(false)
    val showConfirmDeleteAllRegistryDialog: StateFlow<Boolean> = _showConfirmDeleteAllRegistryDialog

    private val _showAddPaymentDialog = MutableStateFlow(false)
    val showAddPaymentDialog: StateFlow<Boolean> = _showAddPaymentDialog

    private val _showAddDebtDialog = MutableStateFlow(false)
    val showAddDebtDialog: StateFlow<Boolean> = _showAddDebtDialog

    private val _paymentAmount = MutableStateFlow("")
    val paymentAmount: StateFlow<String> = _paymentAmount

    private val _debtAmount = MutableStateFlow("")
    val debtAmount: StateFlow<String> = _debtAmount

    private val _debtDetails = MutableStateFlow("")
    val debtDetails: StateFlow<String> = _debtDetails

    private val _paymentMethod: MutableStateFlow<PaymentMethods?> = MutableStateFlow(null)
    val paymentMethod: StateFlow<PaymentMethods?> = _paymentMethod

    private val _showPaymentMethodSelector = MutableStateFlow(false)
    val showPaymentMethodSelector: StateFlow<Boolean> = _showPaymentMethodSelector

    fun getDetails(accountId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                _accountDetails.value = getCurrentAccountDetails(accountId)
            }.await()
            _isLoading.value = false
        }
    }

    fun addDebt() {
        val newTrans = TransactionModel(
            clientId = accountDetails.value!!.id,
            amount = debtAmount.value,
            details = debtDetails.value,
            date = LocalDate.now()
        )

        viewModelScope.launch(Dispatchers.IO) {
            addTransaction(newTrans)
        }
    }

    fun addPayment() {
        val newTrans = TransactionModel(
            clientId = accountDetails.value!!.id,
            amount = "-${paymentAmount.value}",
            details = "Pago realizado con ${paymentMethod.value?.method}",
            date = LocalDate.now()
        )

        viewModelScope.launch(Dispatchers.IO) {
            addTransaction(newTrans)
        }
    }

    fun isAllPaymentData(): Boolean =
        paymentAmount.value.isNotBlank() && paymentMethod.value != null

    fun isAllDebtData(): Boolean = debtAmount.value.isNotBlank() && debtDetails.value.isNotBlank()

    fun modifyShowPaymentMethodSelector(show: Boolean) {
        _showPaymentMethodSelector.value = show
    }

    fun modifyPaymentMethod(newValue: PaymentMethods) {
        _paymentMethod.value = newValue
    }

    fun cleanPaymentData() {
        _paymentAmount.value = ""
        _paymentMethod.value = null
        _isEditablePayment.value = false
    }

    fun cleanDebtData() {
        _debtAmount.value = ""
        _debtDetails.value = ""
        _isEditableDebt.value = false
    }

    fun modifyShowConfirmDialog(show: Boolean) {
        _showConfirmDeleteAllRegistryDialog.value = show
    }


    fun modifyShowAddPaymentDialog(show: Boolean) {
        _showAddPaymentDialog.value = show
    }

    fun modifyShowAddDebtDialog(show: Boolean) {
        _showAddDebtDialog.value = show
    }

    fun modifyDebtAmount(newValue: String) {
        _debtAmount.value = newValue
    }

    fun modifyDebtDetails(newValue: String) {
        _debtDetails.value = newValue
    }

    fun modifyAmountPayment(newValue: String) {
        _paymentAmount.value = newValue
    }

    fun deleteAllTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllTransactionsByClientId(accountDetails.value!!.id)
        }
    }

    fun assignDebtDataAndShowDialog(debt: TransactionModel) {
        _isEditableDebt.value = true
        _transactionDataToModification.value = debt
        _debtAmount.value = debt.amount
        _debtDetails.value = debt.details
        _showAddDebtDialog.value = true
    }

    fun assignPaymentDataAndShowDialog(payment: TransactionModel) {
        _isEditablePayment.value = true
        _transactionDataToModification.value = payment
        val paymentMethodString = payment.details.substringAfter("con ")
        val paymentMethod =
            if (paymentMethodString == PaymentMethods.Cash.method) PaymentMethods.Cash else PaymentMethods.MoneyTransfer
        _paymentAmount.value = payment.amount
        _paymentMethod.value = paymentMethod
        _showAddPaymentDialog.value = true
    }

    private fun clearDebtDataAndTransactionDataToModify() {
        _transactionDataToModification.value = null
        _debtAmount.value = ""
        _debtDetails.value = ""
        _showAddDebtDialog.value = false
        _isEditableDebt.value = false
    }

    private fun clearPaymentDataAndTransactionDataToModify() {
        _transactionDataToModification.value = null
        _paymentAmount.value = ""
        _paymentMethod.value = null
        _showAddPaymentDialog.value = false
        _isEditablePayment.value = false
    }

    fun modifyDebtOnDataLayer() {
        val transaction = _transactionDataToModification.value?.copy(
            amount = debtAmount.value,
            details = debtDetails.value
        )
        viewModelScope.launch(Dispatchers.IO) {
            async {
                updateTransaction(transaction!!)
            }.await()
            clearDebtDataAndTransactionDataToModify()
        }

    }

    fun modifyPaymentOnDataLayer() {
        val transaction = _transactionDataToModification.value?.copy(
            amount = "-${paymentAmount.value}",
            details = "Pago realizado con ${_paymentMethod.value?.method}"
        )
        viewModelScope.launch(Dispatchers.IO) {
            async {
                updateTransaction(transaction!!)
            }.await()
            clearPaymentDataAndTransactionDataToModify()
        }
    }

    fun deleteTransaction() {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                _transactionDataToModification.value?.let { deleteTransaction(it) }
            }.await()
            clearPaymentDataAndTransactionDataToModify()
            clearDebtDataAndTransactionDataToModify()
        }
    }
}