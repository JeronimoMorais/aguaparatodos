package com.example.aguaparatodos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.aguaparatodos.ui.CreateReportPage
import com.example.aguaparatodos.ui.theme.AguaParaTodosTheme

class CreateReportActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AguaParaTodosTheme {
                CreateReportPage(onCreate = {}, onBack = { finish() })
            }
        }
    }
}