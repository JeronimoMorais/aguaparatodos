package com.example.aguaparatodos

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.aguaparatodos.db.FBDatabase
import com.example.aguaparatodos.ui.nav.BottomNavBar
import com.example.aguaparatodos.ui.nav.BottomNavItem
import com.example.aguaparatodos.ui.nav.MainNavHost
import com.example.aguaparatodos.ui.theme.AguaParaTodosTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    @SuppressLint("ContextCastToActivity")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            val fbDB = remember { FBDatabase() }
//            val viewModel : MainViewModel = viewModel(
//                factory = MainViewModelFactory(fbDB)
//            )
            val activity = LocalContext.current as? Activity
            val navController = rememberNavController()
            AguaParaTodosTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Bem-vindo/a!") },
                            actions = {
                                IconButton(onClick = {
                                    Firebase.auth.signOut()
                                    finish()
                                }) {
                                    Icon(
                                        imageVector =
                                            Icons.AutoMirrored.Filled.ExitToApp,
                                        contentDescription = "Localized description"
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = {
                        val items = listOf(
                            BottomNavItem.AllReportsButton,
                            BottomNavItem.UserReportsButton
                        )
                        BottomNavBar(navController = navController, items)
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            activity?.startActivity(
                                Intent(activity, CreateReportActivity::class.java).setFlags(
                                    FLAG_ACTIVITY_SINGLE_TOP
                                )
                            )
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Report")
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainNavHost(navController = navController)
                    }
                }
            }
        }
    }
}