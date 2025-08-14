package com.example.aguaparatodos.ui.stepper

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.aguaparatodos.CreateReportViewModel
import com.example.aguaparatodos.models.ReportType


@Composable
fun StepReportType(viewModel: CreateReportViewModel) {
    val reportTypes = listOf(
        ReportType.LACK_OF_WATER to "Falta de água",
        ReportType.WATER_LEAK to "Vazamento",
        ReportType.DIRTY_WATER to "Água suja",
        ReportType.OTHER to "Outros"
    )

    val onSelectType = { type: ReportType ->
        viewModel.type = type
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp).fillMaxSize()
    ) {
        reportTypes.forEachIndexed { _, pair ->
            Text(
                text = pair.second,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectType(pair.first) }
                    .clip(shape = RoundedCornerShape(size = 24.dp))
                    .background(
                        if (pair.first == viewModel.type)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                    .padding(12.dp)
            )
        }
    }
}