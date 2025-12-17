package com.danucdev.stocksystem.ui.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danucdev.stocksystem.CardBackgroundSecond
import com.danucdev.stocksystem.DarkFontColor

@Composable
fun CardTitle(text: String) {
    Text(text, fontSize = 24.sp, color = DarkFontColor, fontWeight = FontWeight.Bold)
}

@Composable
fun ScreenTitle(text:String) {
    Text(text, fontSize = 32.sp, color = DarkFontColor, fontWeight = FontWeight.Bold)
}

@Composable
fun CardBody(text: String) {
    Text(text, fontSize = 14.sp, color = DarkFontColor)
}

@Composable
fun ButtonTextItem(text: String, color:Color = CardBackgroundSecond, onClick: () -> Unit) {
    Button(
        onClick = { onClick() }, colors = ButtonDefaults.buttonColors(
            backgroundColor = color
        )
    ) {
        Text(text, color = DarkFontColor)
    }
}

@Composable
fun AccentText(text:String, color: Color = Color.Green.copy(.6f)) {
    Text(
        "$32.500",
        fontSize = 36.sp,
        color = color,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun TextFieldItem(
    value: String,
    enabled: Boolean = true,
    label: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier =
            if (enabled) {
                Modifier.fillMaxWidth()
            } else {
                Modifier.fillMaxWidth().pointerHoverIcon(
                    PointerIcon.Default,
                    overrideDescendants = true
                )
            },
        shape = RoundedCornerShape(4.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = CardBackgroundSecond,
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