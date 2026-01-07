package com.danucdev.stocksystem.ui.screens.turns

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import com.danucdev.stocksystem.CardBackgroundSecond
import com.danucdev.stocksystem.DarkMenuBackground
import com.danucdev.stocksystem.ui.core.AcceptDeclineButtons
import com.danucdev.stocksystem.ui.core.ButtonTextItem
import com.danucdev.stocksystem.ui.core.CardBody
import com.danucdev.stocksystem.ui.core.CardTitle
import com.danucdev.stocksystem.ui.core.LackOfData
import com.danucdev.stocksystem.ui.core.ScreenTitle
import com.danucdev.stocksystem.ui.core.TextFieldItem
import org.koin.compose.viewmodel.koinViewModel

class TurnsScreen : Screen {

    @Composable
    override fun Content() {

        val viewmodel = koinViewModel<TurnsViewModel>()

        val uiState by viewmodel.uiState.collectAsState()
        val courtName by viewmodel.courtName.collectAsState()
        val courtList by viewmodel.courtList.collectAsState()

        Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ScreenTitle("Turnos del día")
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ButtonTextItem("Agendar turno") { }
                    ButtonTextItem("Añadir nueva cancha") { viewmodel.updateManageCourtDialog(true) }
                    ButtonTextItem("Gestionar turnos") { }
                }
            }
        }
        if(uiState.showAddCourtDialog) {
            ManageCourtDialog(courtName = courtName, uiState.showLackOfData) { action, value ->
                when(action) {
                    TurnsActions.CHANGE_COURT_NAME -> viewmodel.updateCourtName(value)
                    TurnsActions.ADD_NEW_COURT -> { viewmodel.addNewCourt() }
                    TurnsActions.DISMISS -> {
                        viewmodel.updateManageCourtDialog(false)
                        viewmodel.cleanData()
                    }
                }
            }
        }
    }

}

enum class TurnsActions {
    CHANGE_COURT_NAME, ADD_NEW_COURT, DISMISS
}

@Composable
fun ManageCourtDialog(
    courtName: String,
    showLackOfData: Boolean,
    onActionDone: (TurnsActions, String) -> Unit
) {
    Dialog(
        onDismissRequest = { onActionDone(TurnsActions.DISMISS, "") },
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = DarkMenuBackground
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CardTitle("Gestionar canchas")
                }
                Spacer(modifier = Modifier.size(0.dp))
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    TextFieldItem(courtName, label = "Nombre de la cancha", onClick = {}) { input ->
                        val newInput = input.replaceFirstChar { char ->
                            if (char.isLowerCase()) char.titlecase() else char.toString()
                        }
                        onActionDone(TurnsActions.CHANGE_COURT_NAME, newInput)
                    }
                }
                AnimatedVisibility(showLackOfData) { LackOfData() }
                Spacer(modifier = Modifier.size(0.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    AcceptDeclineButtons(
                        acceptButtonColor = Color.Green.copy(alpha = .6f),
                        onAcceptButtonClick = {
                            onActionDone(TurnsActions.ADD_NEW_COURT, "")
                        },
                        onDeclineButtonClick = { onActionDone(TurnsActions.DISMISS, "") }
                    )
                }

            }
        }
    }
}

@Composable
private fun CourtItem(court: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp)
            .clickable { }.pointerHoverIcon(PointerIcon.Hand),
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
            CardBody(court)
        }
    }
}