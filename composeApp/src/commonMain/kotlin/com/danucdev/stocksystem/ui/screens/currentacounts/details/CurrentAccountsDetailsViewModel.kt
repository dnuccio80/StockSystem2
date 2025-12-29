package com.danucdev.stocksystem.ui.screens.currentacounts.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.stocksystem.domain.models.CurrentAccountModel
import com.danucdev.stocksystem.domain.models.TransactionModel
import com.danucdev.stocksystem.domain.usecases.currentaccounts.GetCurrentAccountDetails
import com.danucdev.stocksystem.domain.usecases.transactions.AddTransaction
import com.danucdev.stocksystem.domain.usecases.transactions.DeleteTransaction
import com.danucdev.stocksystem.domain.usecases.transactions.GetTransactionsByClientId
import com.danucdev.stocksystem.domain.usecases.transactions.UpdateTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.option.viewModelScopeFactory
import java.time.LocalDate

class CurrentAccountsDetailsViewModel(
    private val getCurrentAccountDetails: GetCurrentAccountDetails,
    getTransactionsByClientId: GetTransactionsByClientId,
    private val addTransaction: AddTransaction,
    private val deleteTransaction: DeleteTransaction,
    private val updateTransaction: UpdateTransaction,

):ViewModel() {

    private val _accountDetails = MutableStateFlow<CurrentAccountModel?>(null)
    val accountDetails:StateFlow<CurrentAccountModel?> = _accountDetails

    private val _transactions: StateFlow<List<TransactionModel>?> = getTransactionsByClientId(accountDetails.value?.id?:1).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val transactions:StateFlow<List<TransactionModel>?> = _transactions

    private val _isLoading = MutableStateFlow(true)
    val isLoading:StateFlow<Boolean> = _isLoading

    private val _totalAmount = MutableStateFlow("$0")
    val totalAmount:StateFlow<String> = _totalAmount

    fun getDetails(accountId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                _accountDetails.value = getCurrentAccountDetails(accountId)

            }.await()
            _isLoading.value = false
            checkDebt()
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
            checkDebt()
        }
    }

    fun addPayment() {
        val newTrans = TransactionModel(
            clientId = accountDetails.value!!.id,
            amount = "-8000",
            details = "Pago realizado",
            date = LocalDate.now()
        )

        viewModelScope.launch(Dispatchers.IO) {
            addTransaction(newTrans)
            checkDebt()
        }
    }

    private fun checkDebt() {
        _totalAmount.value = ""
        val newAmount = _transactions.value?.sumOf {
            it.amount.toInt()
        }?:0
        _totalAmount.value = "$$newAmount"
    }

}