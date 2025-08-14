package com.example.aguaparatodos.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aguaparatodos.CreateReportViewModel
import com.example.aguaparatodos.CreateReportViewModelFactory
import com.example.aguaparatodos.db.FBDatabase
import com.example.aguaparatodos.ui.stepper.StepProgressBar
import com.example.aguaparatodos.ui.stepper.StepReportDescription
import com.example.aguaparatodos.ui.stepper.StepReportImages
import com.example.aguaparatodos.ui.stepper.StepReportLocation
import com.example.aguaparatodos.ui.stepper.StepReportType

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReportPage(onBack: () -> Unit, onCreate: () -> Unit) {
    val fbDb = FBDatabase()
    val viewModel: CreateReportViewModel = viewModel (
        factory = CreateReportViewModelFactory(fbDb)
    )
    val activity = LocalContext.current as? Activity
    var step by remember { mutableStateOf(0) }
    var loading by remember { mutableStateOf(false) }
    val steps = listOf(
        "Tipo",
        "Imagens",
        "Local",
        "Descrição",
    )

    fun validateNextStep(): Boolean {
        return when (step) {
            0 -> viewModel.isValidType()
            1 -> viewModel.isValidImages()
            2 -> viewModel.isValidAddress()
            3 -> viewModel.isValidDescription()
            else -> false
        }
    }

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
                        if (step < steps.lastIndex) {
                            step++
                        } else {
                            loading = true
                            viewModel.addReport()
                            loading = false
                            Toast.makeText(activity?.applicationContext, "Denúncia criada com sucesso!", Toast.LENGTH_LONG).show()
                            activity?.finish()
                        }
                    },
                    enabled = validateNextStep() && !loading
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text(if (step < 3) "Próximo" else "Criar")
                    }
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
                0 -> StepReportType(viewModel)
                1 -> StepReportImages()
                2 -> StepReportLocation(viewModel)
                3 -> StepReportDescription(viewModel)
            }
        }
    }
}

@Preview
@Composable
fun CreateReportPagePreview() {
    CreateReportPage(onCreate = {}, onBack = {})
}
