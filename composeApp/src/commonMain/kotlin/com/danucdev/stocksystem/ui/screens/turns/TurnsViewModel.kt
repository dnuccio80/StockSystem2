package com.danucdev.stocksystem.ui.screens.turns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.models.CourtModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TurnsViewModel(private val repository: RepositoryImpl):ViewModel() {

    data class UiState(
        val isLoading:Boolean = false,
        val showAddCourtDialog:Boolean = false,
        val showLackOfData:Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _courtName = MutableStateFlow("")
    val courtName:StateFlow<String> = _courtName.asStateFlow()

    private val _courtList = repository.getAllCourts().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val courtList: StateFlow<List<CourtModel>> = _courtList
        
    fun updateManageCourtDialog(show:Boolean) {
        _uiState.value = _uiState.value.copy(showAddCourtDialog = show)
    }

    fun updateCourtName(newValue:String) {
        _courtName.value = newValue
    }

    fun cleanData() {
        updateCourtName("")
        _uiState.value = _uiState.value.copy(showLackOfData = false)
    }

    fun addNewCourt() {

        if(courtName.value.isBlank()) {
            _uiState.value = _uiState.value.copy(showLackOfData = true)
            return
        }
        val newCourt = CourtModel(
            courtName = courtName.value
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNewCourt(newCourt)
        }
        _uiState.value = _uiState.value.copy(showAddCourtDialog = false)
        cleanData()
    }


}