package com.example.aguaparatodos.ui.stepper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aguaparatodos.CreateReportViewModel

@Composable
fun StepReportDescription(viewModel: CreateReportViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp).fillMaxSize()
    ) {
        OutlinedTextField(
            modifier = Modifier.Companion.fillMaxWidth(),
            label = { Text("Descrição (opcional)") },
            placeholder = { Text("Insira mais informações sobre sua denúncia.") },
            value = viewModel.description,
            onValueChange = { viewModel.description = it },
            minLines = 10
        )
    }
}