package com.danucdev.stocksystem.ui.screens.currentacounts.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.stocksystem.domain.models.CurrentAccountModel
import com.danucdev.stocksystem.domain.usecases.currentaccounts.GetCurrentAccountDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CurrentAccountsDetailsViewModel(
    private val getCurrentAccountDetails: GetCurrentAccountDetails
):ViewModel() {

    private val _accountDetails = MutableStateFlow<CurrentAccountModel?>(null)
    val accountDetails:StateFlow<CurrentAccountModel?> = _accountDetails

    fun getDetails(accountId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _accountDetails.value = getCurrentAccountDetails(accountId)
        }
    }

}