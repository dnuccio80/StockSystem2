package com.danucdev.stocksystem.ui.screens.currentacounts.openaccounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.stocksystem.domain.usecases.clients.GetAllClients
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class OpenAccountsViewModel(getAllClients: GetAllClients) : ViewModel() {

    private val _querySearchCurrentAccount = MutableStateFlow("")
    val querySearchCurrentAccount: StateFlow<String> = _querySearchCurrentAccount

    private val _querySearchClient = MutableStateFlow("")
    val querySearchClient: StateFlow<String> = _querySearchClient

    private val _clientName = MutableStateFlow("")
    val clientName: StateFlow<String> = _clientName

    private val _showAddCurrentAccountDialog = MutableStateFlow(false)
    val showAddCurrentAccountDialog: StateFlow<Boolean> = _showAddCurrentAccountDialog

    private val _showClientDropdownMenu = MutableStateFlow(false)
    val showClientDropdownMenu: StateFlow<Boolean> = _showClientDropdownMenu

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val _clientsList = querySearchClient.debounce(300).flatMapLatest { query -> getAllClients(query) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val clientsList = _clientsList

    fun updateQuerySearchCurrentAccount(newValue:String) {
        _querySearchCurrentAccount.value = newValue
    }

    fun updateQueryClientName(newValue:String) {
        _querySearchClient.value = newValue
    }

    fun updateShowAddAccount(show: Boolean) {
        _showAddCurrentAccountDialog.value = show
    }

    fun updateClientSelected(newValue: String) {
        _clientName.value = newValue
    }

    fun updateShowClientDropdownMenu(newValue: Boolean){
        _showClientDropdownMenu.value = newValue
    }

    fun isAllData():Boolean = _clientName.value.isNotBlank()

    fun addNewCurrentAccount() {
        // LLAMAR A USE CASE (TODAVIA NO HECHO)
    }
}