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

):ViewModel() {

    private val _accountDetails = MutableStateFlow<CurrentAccountModel?>(null)
    val accountDetails:StateFlow<CurrentAccountModel?> = _accountDetails

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _transactions: StateFlow<List<TransactionModel>?> = accountDetails.filterNotNull().flatMapLatest { account ->
        getTransactionsByClientId(account.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val transactions = _transactions

    private val _isLoading = MutableStateFlow(true)
    val isLoading:StateFlow<Boolean> = _isLoading

    private val _totalAmount: StateFlow<Long> = transactions.filterNotNull().map { list ->
        list.sumOf { transaction ->
            transaction.amount.toLong()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)
    val totalAmount = _totalAmount

    private val _showConfirmDialog = MutableStateFlow(false)
    val showConfirmDialog:StateFlow<Boolean> = _showConfirmDialog

    private val _showAddPaymentDialog = MutableStateFlow(false)
    val showAddPaymentDialog:StateFlow<Boolean> = _showAddPaymentDialog

    private val _paymentAmount = MutableStateFlow("")
    val paymentAmount:StateFlow<String> = _paymentAmount

    private val _paymentMethod:MutableStateFlow<PaymentMethods?> = MutableStateFlow(null)
    val paymentMethod:StateFlow<PaymentMethods?> = _paymentMethod

    private val _showPaymentMethodSelector = MutableStateFlow(false)
    val showPaymentMethodSelector:StateFlow<Boolean> = _showPaymentMethodSelector

    fun getDetails(accountId:Int) {
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
            amount = "10000",
            details = "Turno con luz",
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

    fun isAllPaymentData():Boolean = paymentAmount.value.isNotBlank() && paymentMethod.value != null

    fun modifyShowPaymentMethodSelector(show:Boolean) {
        _showPaymentMethodSelector.value = show
    }

    fun modifyPaymentMethod(newValue: PaymentMethods) {
        _paymentMethod.value = newValue
    }

    fun cleanPaymentData() {
        _paymentAmount.value = ""
        _paymentMethod.value = null

    }

    fun modifyShowConfirmDialog(show:Boolean) {
        _showConfirmDialog.value = show
    }


    fun modifyShowAddPaymentDialog(show:Boolean) {
        _showAddPaymentDialog.value = show
    }

    fun modifyAmountPayment(newValue:String) {
        _paymentAmount.value = newValue
    }

    fun deleteAllTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllTransactionsByClientId(accountDetails.value!!.id)
        }
    }

}