package com.danucdev.stocksystem.ui.screens.concessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danucdev.stocksystem.domain.models.ClientModel
import com.danucdev.stocksystem.domain.models.ConcessionModel
import com.danucdev.stocksystem.domain.usecases.concessions.AddConcession
import com.danucdev.stocksystem.domain.usecases.concessions.DeleteConcession
import com.danucdev.stocksystem.domain.usecases.concessions.GetConcessions
import com.danucdev.stocksystem.domain.usecases.concessions.UpdateConcession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConcessionsViewModel(
    private val addConcession: AddConcession,
    private val deleteConcession: DeleteConcession,
    getConcessions: GetConcessions,
    private val updateConcession: UpdateConcession,
) : ViewModel() {

    private val _concessionId = MutableStateFlow(0)

    private val _articleName = MutableStateFlow("")
    val articleName: StateFlow<String> = _articleName

    private val _price = MutableStateFlow("")
    val price: StateFlow<String> = _price

    private val _manageStock = MutableStateFlow(false)
    val manageStock: StateFlow<Boolean> = _manageStock

    private val _currentStock = MutableStateFlow("")
    val currentStock: StateFlow<String> = _currentStock

    private val _adviceStock = MutableStateFlow("")
    val adviceStock: StateFlow<String> = _adviceStock

    private val _questionClicked = MutableStateFlow(false)
    val questionClicked: StateFlow<Boolean> = _questionClicked

    private val _showAddArticleDialog = MutableStateFlow(false)
    val showAddArticleDialog: StateFlow<Boolean> = _showAddArticleDialog

    private val _showEditArticleDialog = MutableStateFlow(false)
    val showEditArticleDialog: StateFlow<Boolean> = _showEditArticleDialog

    private val _isAllData = MutableStateFlow(false)
    val isAllData: StateFlow<Boolean> = _isAllData

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val _concessionsList = query.debounce(300).flatMapLatest { query ->
        getConcessions(query)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val concessionsList = _concessionsList

    fun updateArticleName(newValue: String) {
        _articleName.value = newValue
    }

    fun updatePrice(newValue: String) {
        _price.value = newValue
    }

    fun updateManageStock() {
        _manageStock.value = !_manageStock.value
    }

    fun updateCurrentStock(newValue: String) {
        _currentStock.value = newValue
    }

    fun updateAdviceStock(newValue: String) {
        _adviceStock.value = newValue
    }

    fun updateQuestionClicked() {
        _questionClicked.value = !questionClicked.value
    }

    fun updateShowAddArticleDialog() {
        _showAddArticleDialog.value = !_showAddArticleDialog.value
    }

    fun updateShowEditArticleDialog() {
        _showEditArticleDialog.value = !_showEditArticleDialog.value
    }

    fun updateQuery(newValue: String) {
        _query.value = newValue
    }

    fun cleanAllData() {
        _concessionId.value = 0
        _articleName.value = ""
        _price.value = ""
        _manageStock.value = false
        _currentStock.value = ""
        _adviceStock.value = ""
        _questionClicked.value = false
    }

    fun isAllData(): Boolean {
        return if (_manageStock.value) _articleName.value.isNotBlank() && _price.value.isNotBlank() && _currentStock.value.isNotBlank() && _adviceStock.value.isNotBlank()
        else _articleName.value.isNotBlank() && _price.value.isNotBlank()
    }

    fun addNewConcession() {
        val concession = ConcessionModel(
            name = _articleName.value,
            price = _price.value,
            stock = if (_manageStock.value) _currentStock.value else null,
            adviceStock = if (_manageStock.value) _adviceStock.value else null
        )

        viewModelScope.launch(Dispatchers.IO) {
            addConcession(concession)
        }
    }

    fun updateConcession() {
        val concession = ConcessionModel(
            id = _concessionId.value,
            name = _articleName.value,
            price = _price.value,
            stock = if (_manageStock.value) _currentStock.value else null,
            adviceStock = if (_manageStock.value) _adviceStock.value else null
        )

        viewModelScope.launch(Dispatchers.IO) {
            updateConcession(concession)
        }
    }

    fun deleteConcessionById() {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                deleteConcession(_concessionId.value)
            }.await()
            cleanAllData()
        }
    }

    fun assignConcessionData(concession: ConcessionModel) {
        _concessionId.value = concession.id
        _articleName.value = concession.name
        _price.value = concession.price
        _manageStock.value = concession.stock != null
        _currentStock.value = concession.stock ?: ""
        _adviceStock.value = concession.adviceStock ?: ""
    }

}