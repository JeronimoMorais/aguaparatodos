package com.example.aguaparatodos.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object AllReports : Route
    @Serializable
    data object UserReports : Route
}

sealed class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: Route)
{
    data object AllReportsButton :
        BottomNavItem("Ocorrências", Icons.Default.Home, Route.AllReports)
    data object UserReportsButton :
        BottomNavItem("Minhas ocorrências", Icons.Default.Favorite, Route.UserReports)
}