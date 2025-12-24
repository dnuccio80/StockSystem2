package com.danucdev.stocksystem.ui.screens.currentacounts.openaccounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.stocksystem.domain.models.CurrentAccountModel
import com.danucdev.stocksystem.domain.usecases.clients.GetAllClients
import com.danucdev.stocksystem.domain.usecases.currentaccounts.AddNewCurrentAccount
import com.danucdev.stocksystem.domain.usecases.currentaccounts.GetCurrentAccounts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OpenAccountsViewModel(
    getAllClients: GetAllClients,
    getCurrentAccounts: GetCurrentAccounts,
    private val addNewCurrentAccount: AddNewCurrentAccount,
) : ViewModel() {

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
    private val _clientsList =
        querySearchClient.debounce(300).flatMapLatest { query -> getAllClients(query) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val clientsList = _clientsList

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val _currentAccountsList =
        querySearchCurrentAccount.debounce(300).flatMapLatest { query ->
            getCurrentAccounts(query)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val currentAccountsList: StateFlow<List<CurrentAccountModel>> = _currentAccountsList

    private val _alreadyExist:MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val alreadyExist:StateFlow<Boolean?> = _alreadyExist

    fun updateQuerySearchCurrentAccount(newValue: String) {
        _querySearchCurrentAccount.value = newValue
    }

    fun updateQueryClientName(newValue: String) {
        _querySearchClient.value = newValue
    }

    fun updateShowAddCurrentAccountDialog(show: Boolean) {
        _showAddCurrentAccountDialog.value = show
    }

    fun updateClientSelected(newValue: String) {
        _clientName.value = newValue
    }

    fun cleanData() {
        updateClientSelected("")
        updateQueryClientName("")
        _alreadyExist.value = null
    }

    fun updateShowClientDropdownMenu(newValue: Boolean) {
        _showClientDropdownMenu.value = newValue
    }

    fun isAllData(): Boolean = _clientName.value.isNotBlank()

    fun tryAddCurrentAccount() {
        val client = clientsList.value.first { it.name == clientName.value }
        val newCurrentAccount = CurrentAccountModel(
            id = client.id,
            clientName = client.name,
            amount = "0"
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _alreadyExist.value = addNewCurrentAccount(newCurrentAccount)
            }
        }
    }
}