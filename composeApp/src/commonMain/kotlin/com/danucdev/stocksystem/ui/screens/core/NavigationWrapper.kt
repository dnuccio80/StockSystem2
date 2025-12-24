package com.danucdev.stocksystem.ui.screens.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsTennis
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.danucdev.stocksystem.CardBackgroundFirst
import com.danucdev.stocksystem.CardBackgroundSecond
import com.danucdev.stocksystem.DarkAppBackground
import com.danucdev.stocksystem.DarkFontColor
import com.danucdev.stocksystem.ui.navigation.MenuItemData
import com.danucdev.stocksystem.ui.navigation.Routes
import com.danucdev.stocksystem.ui.screens.clients.ClientsScreen
import com.danucdev.stocksystem.ui.screens.currentacounts.openaccounts.OpenAccountsScreen
import com.danucdev.stocksystem.ui.screens.concessions.ConcessionScreen
import com.danucdev.stocksystem.ui.screens.currentacounts.details.CurrentAccountDetailsScreen
import com.danucdev.stocksystem.ui.screens.mainpanel.MainPanelScreen
import com.danucdev.stocksystem.ui.screens.tournaments.TournamentsScreen
import com.danucdev.stocksystem.ui.screens.turns.TurnsScreen

@Composable
fun NavigationWrapper() {

    val navController = rememberNavController()

    val menuItemList = listOf(
        MenuItemData(title = "Panel Principal", icon = Icons.Filled.Home, route = Routes.MainPanel),
        MenuItemData(title = "Turnos", icon = Icons.Filled.SportsTennis, route = Routes.Turns),
        MenuItemData(title = "Torneos", icon = Icons.Filled.MilitaryTech, route = Routes.Tournaments),
        MenuItemData(title = "Clientes", icon = Icons.Filled.Person, route = Routes.Clients),
        MenuItemData(title = "Cuentas Corrientes", icon = Icons.Filled.AccountBox, route = Routes.OpenAccounts),
        MenuItemData(title = "Inventario", icon = Icons.Filled.Fastfood, route = Routes.Inventory),
    )

    var menuItemSelected by remember { mutableStateOf(Routes.MainPanel.route) }

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
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .width(250.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackgroundSecond),
                elevation = CardDefaults.cardElevation(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    menuItemList.forEach { item ->
                        MenuItem(item, menuItemSelected) { newMenuItemSelected ->
                            menuItemSelected = newMenuItemSelected
                            navController.navigate(newMenuItemSelected) {
                                popUpTo(navController.graph.startDestinationRoute!!) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                }
            }
            NavHost(
                navController = navController,
                startDestination = Routes.MainPanel.route,
            ) {
                composable(Routes.MainPanel.route) { MainPanelScreen() }
                composable(Routes.Turns.route) { TurnsScreen() }
                composable(Routes.Tournaments.route) { TournamentsScreen() }
                composable(Routes.Clients.route) { ClientsScreen() }
                composable(Routes.OpenAccounts.route) { OpenAccountsScreen() }
                composable(Routes.Inventory.route) { ConcessionScreen() }
                composable(Routes.OpenAccountDetails.route) { backStackEntry ->

                    val accountId = backStackEntry
                        .destination
                        .route
                        ?.substringAfter("OpenAccountDetails/")
                        ?.toIntOrNull() ?: 0

                    CurrentAccountDetailsScreen(clientId = accountId)
                }
            }
        }
    }
}

@Composable
private fun MenuItem(
    itemData: MenuItemData,
    menuItemSelected: String,
    onClick: (String) -> Unit,
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(
                    if (menuItemSelected == itemData.route.route) DarkAppBackground.copy(
                        alpha = .6f
                    ) else if (isHovered) CardBackgroundFirst else Color.Transparent
                )
                .hoverable(interactionSource)
                .clickable { onClick(itemData.route.route) }

    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(itemData.icon, contentDescription = "menu item", tint = DarkFontColor)
            Text(itemData.title, color = DarkFontColor, fontSize = 14.sp)
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