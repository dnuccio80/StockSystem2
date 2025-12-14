package com.danucdev.stocksystem.ui.screens.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danucdev.stocksystem.CardBackgroundFirst
import com.danucdev.stocksystem.CardBackgroundSecond
import com.danucdev.stocksystem.DarkAppBackground
import com.danucdev.stocksystem.DarkCardBackground
import com.danucdev.stocksystem.DarkFontColor

@Composable
fun NavigationWrapper() {
    Scaffold(
        topBar = { TopBar() },
        containerColor = DarkAppBackground
    ) { innerPadding ->
        Row(
            modifier = Modifier.fillMaxWidth().padding(
                top = innerPadding.calculateTopPadding(),
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(64.dp)
        ) {
            Card(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .width(250.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackgroundSecond),
                elevation = CardDefaults.cardElevation(16.dp)
            ) { }
            Card(
                modifier = Modifier.width(300.dp).height(230.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCardBackground),
                elevation = CardDefaults.cardElevation(16.dp)
            ) {

            }
            Card(
                modifier = Modifier.width(300.dp).height(230.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackgroundFirst),
                elevation = CardDefaults.cardElevation(16.dp)
            ) {

            }

            Card(
                modifier = Modifier.width(300.dp).height(230.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackgroundSecond),
                elevation = CardDefaults.cardElevation(16.dp)
            ) {

            }


        }

    }
}


@Composable
fun TopBar() {

    var textValue by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp).background(DarkAppBackground),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(modifier = Modifier.width(250.dp), contentAlignment = Alignment.Center) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "",
                    tint = DarkFontColor,

                    )
                Text(
                    "GP PADEL",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkFontColor
                )
            }
        }
        TextField(
            value = textValue,
            onValueChange = { textValue = it },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(4.dp),
            trailingIcon = {
                if (textValue.isNotBlank()) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .clickable {
                                textValue = ""
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
            placeholder = { Text("Buscar") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "",
                    tint = DarkFontColor
                )
            })
        Card(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color.Blue)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("A")
            }
        }
        Card(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color.Blue)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("A")
            }
        }
        Card(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color.Blue)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("A")
            }
        }
    }
}