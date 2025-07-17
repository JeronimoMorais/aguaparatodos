package com.example.aguaparatodos.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aguaparatodos.ui.stepper.StepProgressBar
import com.example.aguaparatodos.ui.stepper.StepReportDescription
import com.example.aguaparatodos.ui.stepper.StepReportImages
import com.example.aguaparatodos.ui.stepper.StepReportLocation
import com.example.aguaparatodos.ui.stepper.StepReportType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReportPage(onBack: () -> Unit, onCreate: () -> Unit) {
    var step by remember { mutableStateOf(0) }
    val steps = listOf(
        "Tipo",
        "Imagens",
        "Local",
        "Descrição"
    )

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Nova denúncia", style = MaterialTheme.typography.titleMedium ) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        bottomBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    enabled = step > 0,
                    onClick = { if (step > 0) step-- }
                ) {
                    Text("Anterior")
                }
                Button(
                    onClick = {
                        if (step < steps.lastIndex)
                            step++
                        else
                            onCreate()
                    }
                ) {
                    Text(if (step < 3) "Próximo" else "Criar")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top)
        ) {
            StepProgressBar(step, steps)

            when(step) {
                0 -> StepReportType()
                1 -> StepReportImages()
                2 -> StepReportLocation()
                3 -> StepReportDescription()
            }
        }
    }
}

@Preview
@Composable
fun CreateReportPagePreview() {
    CreateReportPage(onCreate = {}, onBack = {})
}
