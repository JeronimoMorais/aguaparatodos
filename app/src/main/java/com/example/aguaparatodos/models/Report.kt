package com.example.aguaparatodos.models

data class Report(
    val type: ReportType? = null,
    val images: List<String> = emptyList<String>(),
    val address: Address? = null,
    val description: String? = null,
)