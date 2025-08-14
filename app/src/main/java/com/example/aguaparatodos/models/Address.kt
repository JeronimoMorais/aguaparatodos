package com.example.aguaparatodos.models

import com.google.android.gms.maps.model.LatLng

data class Address(
    var location: LatLng? = null,
    var state: String? = null,
    var city: String? = null,
    var neighborhood: String? = null,
    var street: String? = null,
    var number: String? = null,
    var zipcode: String? = null,
) {
    fun isValid(): Boolean {
        return location != null && !state.isNullOrBlank() && !neighborhood.isNullOrBlank() && !street.isNullOrBlank() && !zipcode.isNullOrBlank()
    }

}
