package com.example.aguaparatodos.ui.stepper

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices


@Composable
@Preview(showSystemUi = true)
fun StepReportLocation() {
    var address: Address? = null
    var state by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var neighborhood by rememberSaveable { mutableStateOf("") }
    var street by rememberSaveable { mutableStateOf("") }
    var number by rememberSaveable { mutableStateOf("") }
    var zipcode by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val fuseLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
        )
    }

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            fuseLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val geocoder = Geocoder(context)
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                    if (!addresses.isNullOrEmpty()) {
                        address = addresses[0]

                        state = address.adminArea
                        city = address.subAdminArea
                        neighborhood = address.subLocality
                        street = address.thoroughfare
                        zipcode = address.postalCode
                    }
                }
            }
        }
    }

    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp).fillMaxSize()
    ) {
        OutlinedTextField(
            modifier = Modifier.Companion.fillMaxWidth(),
            label = { Text("Estado") },
            value = state,
            onValueChange = { state = it }
        )
        OutlinedTextField(
            modifier = Modifier.Companion.fillMaxWidth(),
            label = { Text("Cidade") },
            value = city,
            onValueChange = { city = it }
        )
        OutlinedTextField(
            modifier = Modifier.Companion.fillMaxWidth(),
            label = { Text("Bairro") },
            value = neighborhood,
            onValueChange = { neighborhood = it }
        )
        OutlinedTextField(
            modifier = Modifier.Companion.fillMaxWidth(),
            label = { Text("Rua") },
            value = street,
            onValueChange = { street = it }
        )
        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.5f),
                label = { Text("CEP") },
                value = zipcode,
                onValueChange = { zipcode = it }
            )
            OutlinedTextField(
                label = { Text("Número") },
                value = number,
                onValueChange = { number = it }
            )
        }
    }
}