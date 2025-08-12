package com.example.aguaparatodos.models

import androidx.compose.runtime.mutableStateOf

data class User(val nome: String, val email:String, val senha: String, val repeat_password: String) {
    private val _user = mutableStateOf<User?> (null)
    val user : User? get() = _user.value

}