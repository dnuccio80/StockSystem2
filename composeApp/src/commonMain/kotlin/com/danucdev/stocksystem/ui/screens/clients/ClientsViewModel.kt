package com.danucdev.stocksystem.ui.screens.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.usecases.AddClient
import com.danucdev.stocksystem.domain.models.ClientModel
import com.danucdev.stocksystem.domain.usecases.GetAllClients
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class ClientsViewModel(private val addClient: AddClient, getAllClients: GetAllClients) :
    ViewModel() {

    private val _clientName = MutableStateFlow("")
    val clientName: StateFlow<String> = _clientName

    private val _clientLastName = MutableStateFlow("")
    val clientLastName: StateFlow<String> = _clientLastName

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    private val _birthDate = MutableStateFlow<LocalDate?>(null)
    val birthDate:StateFlow<LocalDate?> = _birthDate

    private val _showAddClientDialog = MutableStateFlow(false)
    val showAddClientDialog: StateFlow<Boolean> = _showAddClientDialog

    private val _allClients = getAllClients().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allClients = _allClients

    fun modifyClientName(newValue: String) {
        _clientName.value = newValue
    }

    fun modifyClientLastName(newValue: String) {
        _clientLastName.value = newValue
    }

    fun modifyPhoneNumber(newValue: String) {
        _phoneNumber.value = newValue
    }

    fun showAddClientDialog(show: Boolean) {
        _showAddClientDialog.value = show
    }

    fun modifyBirthDate(newValue:LocalDate) {
        _birthDate.value = newValue
    }

    fun addNewClient() {

        val newClient = ClientModel(
            name = _clientName.value + " " + _clientLastName.value,
            phone = _phoneNumber.value,
            birthDate = _birthDate.value!!
        )

        viewModelScope.launch {
//            repository.addClient(newClient)
            addClient(newClient)
        }
    }

    fun cleanDialogData() {
        _clientName.value = ""
        _clientLastName.value = ""
        _phoneNumber.value = ""
        _birthDate.value = null
    }

}