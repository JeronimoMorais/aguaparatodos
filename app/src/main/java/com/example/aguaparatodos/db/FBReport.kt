package com.example.aguaparatodos.db

import com.example.aguaparatodos.models.Address
import com.example.aguaparatodos.models.Report
import com.example.aguaparatodos.models.ReportType
import com.google.android.gms.maps.model.LatLng

class FBReport {
    var type: ReportType? = null
    var lat: Double? = null
    var lng: Double? = null
    var state: String? = null
    var neighborhood: String? = null
    var street: String? = null
    var number: String? = null
    var zipcode: String? = null
    var description: String? = null

    fun toReport(): Report {
        val latlng = if (lat!=null&&lng!=null) LatLng(lat!!, lng!!) else null
        val address = Address(location = latlng, state = state!!, neighborhood = neighborhood!!, street = street!!, number = number!!, zipcode = zipcode!!)
        return Report(type = type!!, address = address, description =  description!!)
    }
}

fun Report.toFBReport(): FBReport {
    val fbReport = FBReport()

    fbReport.type = this.type
    fbReport.lat = this.address?.location?.latitude ?: 0.0
    fbReport.lng = this.address?.location?.longitude ?: 0.0
    fbReport.state = this.address?.state
    fbReport.neighborhood = this.address?.neighborhood
    fbReport.street = this.address?.street
    fbReport.number = this.address?.number
    fbReport.zipcode = this.address?.zipcode
    fbReport.description = this.description

    return fbReport
}
