package com.example.aguaparatodos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aguaparatodos.db.FBDatabase
import com.example.aguaparatodos.db.toFBReport
import com.example.aguaparatodos.models.Address
import com.example.aguaparatodos.models.Report
import com.example.aguaparatodos.models.ReportType
import com.google.android.gms.maps.model.LatLng

class CreateReportViewModel (private val db: FBDatabase): ViewModel() {
    var type: ReportType? by mutableStateOf(null)
    var address by mutableStateOf(Address())
    var description by mutableStateOf("")

    fun addReport() {
        if (isValidType() && isValidAddress() && isValidImages() && isValidDescription()) {
            db.addReport(Report(type = type, address = address, description = description).toFBReport())
        }
    }

    fun isValidType(): Boolean {
        return type != null
    }

    fun isValidAddress(): Boolean {
        return address.isValid()
    }

    fun isValidDescription(): Boolean {
        return !description.isNullOrBlank()
    }

    fun isValidImages(): Boolean {
        return true
    }

    fun updateLocation(location: LatLng) {
        address = address.copy(location = location)
    }

    fun updateState(state: String) {
        address = address.copy(state = state)
    }

    fun updateCity(city: String) {
        address = address.copy(city = city)
    }

    fun updateNeighborhood(neighborhood: String) {
        address = address.copy(neighborhood = neighborhood)
    }

    fun updateStreet(street: String) {
        address = address.copy(street = street)
    }

    fun updateZipcode(zipcode: String) {
        address = address.copy(zipcode = zipcode)
    }

    fun updateNumber(number: String) {
        address = address.copy(number = number)
    }
}

class CreateReportViewModelFactory(private val db: FBDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateReportViewModel::class.java)) {
            return CreateReportViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}