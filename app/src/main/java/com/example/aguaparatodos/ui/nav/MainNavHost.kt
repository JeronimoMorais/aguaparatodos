package com.example.aguaparatodos.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aguaparatodos.ui.AllReportsPage
import com.example.aguaparatodos.ui.UserReportsPage

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Route.AllReports) {
        composable<Route.AllReports> { AllReportsPage() }
        composable<Route.UserReports> { UserReportsPage() }
    }
}