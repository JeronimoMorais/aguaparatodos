package com.example.aguaparatodos.models

import com.google.android.gms.maps.model.LatLng

data class Address(
    val location: LatLng? = null,
    val state: String? = null,
    val neighborhood: String? = null,
    val street: String? = null,
    val number: String? = null,
    val zipcode: String? = null,
)
