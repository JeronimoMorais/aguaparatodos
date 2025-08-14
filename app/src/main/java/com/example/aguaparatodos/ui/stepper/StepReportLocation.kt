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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.aguaparatodos.CreateReportViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng


@Composable
fun StepReportLocation(viewModel: CreateReportViewModel) {
    var address: Address? = null

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

                        viewModel.updateLocation(LatLng(location.latitude, location.longitude))
                        viewModel.updateState(address.adminArea)
                        viewModel.updateCity(address.subAdminArea)
                        viewModel.updateNeighborhood(address.subLocality)
                        viewModel.updateStreet(address.thoroughfare)
                        viewModel.updateZipcode(address.postalCode)
                    }
                }
            }
        }
    }

    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp).fillMaxSize()
    ) {
        Text(viewModel.address.isValid().toString())
        OutlinedTextField(
            modifier = Modifier.Companion.fillMaxWidth(),
            label = { Text("Estado") },
            value = viewModel.address.state ?: "",
            onValueChange = { viewModel.updateState(it) }
        )
        OutlinedTextField(
            modifier = Modifier.Companion.fillMaxWidth(),
            label = { Text("Cidade") },
            value = viewModel.address.city ?: "",
            onValueChange = { viewModel.updateCity(it) }
        )
        OutlinedTextField(
            modifier = Modifier.Companion.fillMaxWidth(),
            label = { Text("Bairro") },
            value = viewModel.address.neighborhood ?: "",
            onValueChange = { viewModel.updateNeighborhood(it) }
        )
        OutlinedTextField(
            modifier = Modifier.Companion.fillMaxWidth(),
            label = { Text("Rua") },
            value = viewModel.address.street ?: "",
            onValueChange = { viewModel.updateStreet(it) }
        )
        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.5f),
                label = { Text("CEP") },
                value = viewModel.address.zipcode ?: "",
                onValueChange = { viewModel.updateZipcode(it) }
            )
            OutlinedTextField(
                label = { Text("Número") },
                value = viewModel.address.number ?: "",
                onValueChange = { viewModel.updateNumber(it) }
            )
        }
    }
}