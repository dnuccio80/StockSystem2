package com.danucdev.stocksystem.ui.screens.concessions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.danucdev.stocksystem.CardBackgroundSecond
import com.danucdev.stocksystem.DarkFontColor
import com.danucdev.stocksystem.DarkMenuBackground
import com.danucdev.stocksystem.domain.models.ConcessionModel
import com.danucdev.stocksystem.ui.core.AcceptDeclineButtons
import com.danucdev.stocksystem.ui.core.ButtonTextItem
import com.danucdev.stocksystem.ui.core.CardBody
import com.danucdev.stocksystem.ui.core.CardTitle
import com.danucdev.stocksystem.ui.core.ConfirmDialog
import com.danucdev.stocksystem.ui.core.ScreenTitle
import com.danucdev.stocksystem.ui.core.SearchBarItem
import com.danucdev.stocksystem.ui.core.TextFieldItem
import com.danucdev.stocksystem.ui.core.TitleAndButtonRowItemScreenWithSearchBar
import com.danucdev.stocksystem.ui.screens.clients.ClientDataActions
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConcessionScreen() {

    val viewmodel = koinViewModel<ConcessionsViewModel>()

    val articleName by viewmodel.articleName.collectAsState()
    val price by viewmodel.price.collectAsState()
    val currentStock by viewmodel.currentStock.collectAsState()
    val adviceStock by viewmodel.adviceStock.collectAsState()
    val manageStock by viewmodel.manageStock.collectAsState()
    val questionClicked by viewmodel.questionClicked.collectAsState()
    val showAddArticleDialog by viewmodel.showAddArticleDialog.collectAsState()
    val showEditArticleDialog by viewmodel.showEditArticleDialog.collectAsState()
    val query by viewmodel.query.collectAsState()
    val concessionsList by viewmodel.concessionsList.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TitleAndButtonRowItemScreenWithSearchBar(
                title = "Inventario",
                buttonText = "Agregar artículo",
                onButtonClick = { viewmodel.updateShowAddArticleDialog() },
                query = query,
                onSearchValueChange = { viewmodel.updateQuery(it) }
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.verticalScroll(
                    rememberScrollState()
                )
            ) {
                if (concessionsList.isEmpty()) {
                    Box(modifier = Modifier.padding(horizontal = 64.dp)) {
                        CardBody("No hay artículos para mostrar")
                    }
                } else {
                    concessionsList.forEach { concession ->
                        ConcessionItem(
                            concession,
                            onDelete = { viewmodel.deleteConcessionById(concession.id) },
                            onModify = {
                                viewmodel.assignConcessionData(concession)
                                viewmodel.updateShowEditArticleDialog()
                            }
                        )
                    }
                }
            }


        }

        // Add Article
        if (showAddArticleDialog) {
            ConcessionDialog(
                articleName = articleName,
                price = price,
                currentStock = currentStock,
                adviceStock = adviceStock,
                manageStock = manageStock,
                isAllData = viewmodel.isAllData(),
                questionClicked = questionClicked,
                onActionDone = { action, value ->
                    when (action) {
                        ConcessionActions.CHANGE_NAME -> viewmodel.updateArticleName(value)
                        ConcessionActions.CHANGE_PRICE -> viewmodel.updatePrice(value)
                        ConcessionActions.MANAGE_STOCK -> viewmodel.updateManageStock()
                        ConcessionActions.CHANGE_CURRENT_STOCK -> viewmodel.updateCurrentStock(value)
                        ConcessionActions.CHANGE_ADVICE_STOCK -> viewmodel.updateAdviceStock(value)
                        ConcessionActions.QUESTION_CLICKED -> viewmodel.updateQuestionClicked()
                        ConcessionActions.ON_DISMISS -> {
                            viewmodel.updateShowAddArticleDialog()
                            viewmodel.cleanAllData()
                        }

                        ConcessionActions.ADD_ARTICLE -> {
                            viewmodel.addNewConcession()
                            viewmodel.cleanAllData()
                            viewmodel.updateShowAddArticleDialog()
                        }
                    }
                }
            )
        }

        // Edit Article
        if (showEditArticleDialog) {
            ConcessionDialog(
                articleName = articleName,
                price = price,
                currentStock = currentStock,
                isEditedArticle = true,
                adviceStock = adviceStock,
                manageStock = manageStock,
                isAllData = viewmodel.isAllData(),
                questionClicked = questionClicked,
                onActionDone = { action, value ->
                    when (action) {
                        ConcessionActions.CHANGE_NAME -> viewmodel.updateArticleName(value)
                        ConcessionActions.CHANGE_PRICE -> viewmodel.updatePrice(value)
                        ConcessionActions.MANAGE_STOCK -> viewmodel.updateManageStock()
                        ConcessionActions.CHANGE_CURRENT_STOCK -> viewmodel.updateCurrentStock(value)
                        ConcessionActions.CHANGE_ADVICE_STOCK -> viewmodel.updateAdviceStock(value)
                        ConcessionActions.QUESTION_CLICKED -> viewmodel.updateQuestionClicked()
                        ConcessionActions.ON_DISMISS -> {
                            viewmodel.updateShowEditArticleDialog()
                            viewmodel.cleanAllData()
                        }

                        ConcessionActions.ADD_ARTICLE -> {
                            viewmodel.updateConcession()
                            viewmodel.cleanAllData()
                            viewmodel.updateShowEditArticleDialog()
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun ConcessionItem(article: ConcessionModel, onDelete: () -> Unit, onModify: () -> Unit) {

    var showConfirmDeleteClientDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackgroundSecond
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardBody(article.name)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CardBody("price: $${article.price}")
                Button(
                    shape = CircleShape,
                    onClick = { onModify() },
                    modifier = Modifier.size(35.dp),
                    contentPadding = PaddingValues(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkMenuBackground)
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "", tint = DarkFontColor)
                }
                Button(
                    shape = CircleShape,
                    onClick = { showConfirmDeleteClientDialog = true },
                    modifier = Modifier.size(35.dp),
                    contentPadding = PaddingValues(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkMenuBackground)
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "", tint = DarkFontColor)
                }
            }

        }
        if (showConfirmDeleteClientDialog) {
            ConfirmDialog(
                "¿Seguro que querés eliminar este artículo?",
                onConfirm = {
                    onDelete()
                    showConfirmDeleteClientDialog = false
                },
                onDismiss = { showConfirmDeleteClientDialog = false })
        }

    }
}


enum class ConcessionActions {
    CHANGE_NAME, CHANGE_PRICE, MANAGE_STOCK, CHANGE_CURRENT_STOCK, CHANGE_ADVICE_STOCK, QUESTION_CLICKED, ON_DISMISS, ADD_ARTICLE
}

@Composable
fun ConcessionDialog(
    articleName: String,
    price: String,
    currentStock: String,
    isEditedArticle: Boolean = false,
    adviceStock: String,
    manageStock: Boolean,
    questionClicked: Boolean,
    isAllData: Boolean,
    onActionDone: (ConcessionActions, String) -> Unit,
) {

    var showError by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Dialog(
        onDismissRequest = { onActionDone(ConcessionActions.ON_DISMISS, "") },
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = DarkMenuBackground
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CardTitle(if (!isEditedArticle) "Agregar Artículo" else "Editar Artículo")
                }
                Spacer(modifier = Modifier.size(0.dp))
                TextFieldItem(
                    articleName,
                    label = "Nombre del articulo",
                    focusRequester = focusRequester,
                    onClick = {}) { input ->
                    val newInput = input.replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase() else char.toString()
                    }
                    onActionDone(ConcessionActions.CHANGE_NAME, newInput)
                }
                TextFieldItem(price, label = "Precio", onClick = {}) { input ->
                    if (input.isBlank()) {
                        onActionDone(ConcessionActions.CHANGE_PRICE, "")
                    }
                    val newInput = input.filter { it.isDigit() }
                    onActionDone(ConcessionActions.CHANGE_PRICE, newInput)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        onActionDone(
                            ConcessionActions.MANAGE_STOCK,
                            ""
                        )
                    }) {
                    Checkbox(
                        manageStock,
                        onCheckedChange = { onActionDone(ConcessionActions.MANAGE_STOCK, "") })
                    CardBody("Gestionar stock")
                }
                androidx.compose.animation.AnimatedVisibility(manageStock) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        TextFieldItem(currentStock, label = "Stock actual", onClick = {}) { input ->
                            if (input.isBlank()) {
                                onActionDone(ConcessionActions.CHANGE_CURRENT_STOCK, "")
                            }
                            val newInput = input.filter { it.isDigit() }
                            onActionDone(ConcessionActions.CHANGE_CURRENT_STOCK, newInput)
                        }
                        TextFieldItem(
                            adviceStock,
                            label = "Aviso de pocas existencias",
                            onClick = {}) { input ->
                            if (input.isBlank()) {
                                onActionDone(ConcessionActions.CHANGE_ADVICE_STOCK, "")
                            }
                            val newInput = input.filter { it.isDigit() }
                            onActionDone(ConcessionActions.CHANGE_ADVICE_STOCK, newInput)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Card(
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Crossfade(questionClicked) { isClicked ->
                                    Icon(
                                        if (!isClicked) Icons.Filled.QuestionMark else Icons.Filled.Close,
                                        contentDescription = "",
                                        tint = Color.Black,
                                        modifier = Modifier.padding(2.dp)
                                            .clickable {
                                                onActionDone(
                                                    ConcessionActions.QUESTION_CLICKED,
                                                    ""
                                                )
                                            })
                                }
                            }
                            androidx.compose.animation.AnimatedVisibility(questionClicked) {
                                CardBody("Se mostrará un mensaje de alerta de poco stock en el panel principal y en el panel de inventario cuando la cantidad de articulos sea igual o menor que el número que insertes en aviso de pocas existencias.\nSi no queres el aviso, coloca 0 (cero).")
                            }
                        }

                    }
                }
                Spacer(modifier = Modifier.size(0.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    AcceptDeclineButtons(
                        acceptButtonColor = Color.Green.copy(alpha = .6f),
                        onAcceptButtonClick = {
                            if (isAllData) {
                                showError = false
                                onActionDone(ConcessionActions.ADD_ARTICLE, "")
                            } else {
                                showError = true
                            }
                        },
                        onDeclineButtonClick = { onActionDone(ConcessionActions.ON_DISMISS, "") }
                    )
                }
                AnimatedVisibility(visible = showError) {
                    Text("Faltan rellenar datos", color = Color.Red, fontSize = 12.sp)
                }
            }
        }
    }
}