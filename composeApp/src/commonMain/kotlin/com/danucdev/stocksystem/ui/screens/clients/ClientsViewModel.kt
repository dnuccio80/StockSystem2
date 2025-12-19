package com.danucdev.stocksystem.ui.screens.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.usecases.AddClient
import com.danucdev.stocksystem.domain.models.ClientModel
import com.danucdev.stocksystem.domain.usecases.DeleteClient
import com.danucdev.stocksystem.domain.usecases.GetAllClients
import com.danucdev.stocksystem.domain.usecases.UpdateClientData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.logger.Logger
import java.time.LocalDate

class ClientsViewModel(
    private val addClient: AddClient,
    getAllClients: GetAllClients,
    private val deleteClient: DeleteClient,
    private val updateClientData: UpdateClientData

) :
    ViewModel() {

    private val _clientId = MutableStateFlow(0)

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

    private val _showEditClientDialog = MutableStateFlow(false)
    val showEditClientDialog: StateFlow<Boolean> = _showEditClientDialog

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

    fun showEditClientDialog(show: Boolean) {
        _showEditClientDialog.value = show
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
            addClient(newClient)
        }
    }

    fun deleteClientData(clientId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteClient(clientId)
        }
    }

    fun cleanDialogData() {
        _clientName.value = ""
        _clientLastName.value = ""
        _phoneNumber.value = ""
        _birthDate.value = null
    }

    fun assignClientData(client:ClientModel) {

        val name = client.name.substringBefore(" ")
        val lastName = client.name.substringAfter(" ")

        _clientId.value = client.id
        _clientName.value = name
        _clientLastName.value = lastName
        _phoneNumber.value = client.phone
        _birthDate.value = client.birthDate

        println(_clientId.value)
    }

    fun updateClient() {
        val clientData = ClientModel(
            id = _clientId.value,
            name = _clientName.value + " " + _clientLastName.value,
            phone = _phoneNumber.value,
            birthDate = _birthDate.value!!
        )

        viewModelScope.launch(Dispatchers.IO) {
            updateClientData(clientData)
        }
    }

    fun isAllData():Boolean{
        return _clientName.value.isNotBlank() && _clientLastName.value.isNotBlank() && _phoneNumber.value.isNotBlank() && _birthDate.value != null
    }

}