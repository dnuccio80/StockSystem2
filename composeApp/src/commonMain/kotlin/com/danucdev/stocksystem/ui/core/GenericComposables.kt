package com.danucdev.stocksystem.ui.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.danucdev.stocksystem.CardBackgroundSecond
import com.danucdev.stocksystem.DarkFontColor
import com.danucdev.stocksystem.DarkMenuBackground

@Composable
fun CardTitle(text: String) {
    Text(text, fontSize = 24.sp, color = DarkFontColor, fontWeight = FontWeight.Bold)
}

@Composable
fun ScreenTitle(text: String) {
    Text(text, fontSize = 32.sp, color = DarkFontColor, fontWeight = FontWeight.Bold)
}

@Composable
fun CardBody(text: String) {
    Text(text, fontSize = 14.sp, color = DarkFontColor)
}

@Composable
fun ButtonTextItem(text: String, color: Color = CardBackgroundSecond, onClick: () -> Unit) {
    Button(
        onClick = { onClick() }, colors = ButtonDefaults.buttonColors(
            backgroundColor = color
        )
    ) {
        Text(text, color = DarkFontColor)
    }
}

@Composable
fun AccentText(text: String, color: Color = Color.Green.copy(.6f)) {
    Text(
        text,
        fontSize = 36.sp,
        color = color,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun TextFieldItem(
    value: String,
    enabled: Boolean = true,
    focusRequester:FocusRequester? = null,
    label: String,
    clickable: Boolean = false,
    onClick: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier =
            if (!enabled && clickable) {
                Modifier.fillMaxWidth().clickable {
                    onClick()
                }.pointerHoverIcon(
                    PointerIcon.Default,
                    overrideDescendants = true
                )
            } else if(focusRequester != null) {
                Modifier.fillMaxWidth().focusRequester(focusRequester)
            }
            else {
                Modifier.fillMaxWidth()
            },
        shape = RoundedCornerShape(4.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = CardBackgroundSecond.copy(alpha = .6f),
            unfocusedContainerColor = CardBackgroundSecond,
            focusedTextColor = DarkFontColor,
            unfocusedTextColor = DarkFontColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = CardBackgroundSecond,
            disabledTextColor = DarkFontColor
        ),
        maxLines = 1,
        singleLine = true,
        enabled = enabled,
        label = { CardBody(label) }
    )
}

@Composable
fun TitleAndButtonRowItemScreen(title:String, buttonText:String, onButtonClick:() -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        ScreenTitle(title)
        ButtonTextItem(buttonText) { onButtonClick() }
    }
}

@Composable
fun TitleAndButtonRowItemScreenWithSearchBar(title:String, buttonText:String, onButtonClick:() -> Unit, query:String, onSearchValueChange:(String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleAndButtonRowItemScreen(title, buttonText) { onButtonClick() }
        Spacer(modifier = Modifier.size(0.dp))
        SearchBarItem(query) { onSearchValueChange(it) }
        Spacer(modifier = Modifier.size(0.dp))
    }
}

@Composable
fun SearchBarItem(
    query: String,
    onValueChange:(String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = { input ->
            val newInput = input.replaceFirstChar { char ->
                if (char.isLowerCase()) char.titlecase() else char.toString()
            }
            onValueChange(newInput)
//            viewmodel.updateQueryClientName(newInput)
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
        shape = RoundedCornerShape(4.dp),
        trailingIcon = {
            if (query.isNotBlank()) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            onValueChange("")
//                            viewmodel.updateQueryClientName("")
                        }
                        .pointerHoverIcon(
                            PointerIcon.Default
                        ),
                    tint = DarkFontColor
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = CardBackgroundSecond,
            unfocusedContainerColor = CardBackgroundSecond,
            focusedTextColor = DarkFontColor,
            unfocusedTextColor = DarkFontColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,

            ),
        maxLines = 1,
        singleLine = true,
        placeholder = { androidx.compose.material3.Text("Buscar por nombre") },
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = "",
                tint = DarkFontColor
            )
        })
}

@Composable
fun ConfirmDialog(text: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(shape = RoundedCornerShape(4.dp), colors = CardDefaults.cardColors(containerColor = DarkMenuBackground)) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CardBody(text)
                AcceptDeclineButtons(
                    acceptButtonColor = Color.Red.copy(alpha = .6f),
                    acceptText = "Eliminar",
                    onAcceptButtonClick = { onConfirm() },
                    onDeclineButtonClick = { onDismiss() }
                )
            }
        }
    }
}

@Composable
fun AcceptDeclineButtons(
    acceptText: String = "Aceptar",
    declineText: String = "Cancelar",
    acceptButtonColor: Color = CardBackgroundSecond,
    declineButtonColor: Color = CardBackgroundSecond,
    onAcceptButtonClick: () -> Unit,
    onDeclineButtonClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ButtonTextItem(declineText, declineButtonColor) { onDeclineButtonClick() }
        ButtonTextItem(acceptText, acceptButtonColor) { onAcceptButtonClick() }
    }
}