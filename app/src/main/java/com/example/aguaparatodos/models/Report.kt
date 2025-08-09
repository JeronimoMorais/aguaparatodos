package com.example.aguaparatodos.models

data class Report(
    val type: ReportType,
    val images: List<String>,
    val state: String,
    val city: String,
    val neighborhood: String,
    val street: String,
    val number: String? = null,
    val description: String,
)