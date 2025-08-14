package com.example.aguaparatodos

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aguaparatodos.db.FBDatabase
import com.example.aguaparatodos.db.FBUser
import com.example.aguaparatodos.models.User

class MainViewModel (private val db: FBDatabase): ViewModel(), FBDatabase.Listener {
    private val _user = mutableStateOf<User?> (null)

    val user : User?
        get() = _user.value

    init {
        db.setListener(this)
    }

    override fun onUserLoaded(user: FBUser) {
        _user.value = user.toUser()
    }

    override fun onUserSignOut() {}
}

class MainViewModelFactory(private val db : FBDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}