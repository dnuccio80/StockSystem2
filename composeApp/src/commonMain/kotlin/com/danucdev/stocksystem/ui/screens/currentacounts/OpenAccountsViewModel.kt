package com.danucdev.stocksystem.ui.screens.currentacounts

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OpenAccountsViewModel() : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _showAddAccountDialog = MutableStateFlow(false)
    val showAddAccountDialog: StateFlow<Boolean> = _showAddAccountDialog

    fun updateQuery(newValue:String) {
        _query.value = newValue
    }

    fun updateShowAddAccount(show: Boolean) {
        _showAddAccountDialog.value = show
    }
}