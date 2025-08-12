package com.example.aguaparatodos

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aguaparatodos.ui.theme.AguaParaTodosTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AguaParaTodosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginPage(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@SuppressLint("ContextCastToActivity")
//@Preview(showBackground = true)
@Composable
fun LoginPage(modifier: Modifier = Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    var senha by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo do aplicativo",
            modifier = Modifier
                .size(160.dp)
                .padding(top = 16.dp),
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .widthIn(max = 256.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            OutlinedTextField(
                value = email,
                label = { Text(text = "Digite seu e-mail") },
                modifier = modifier.fillMaxWidth(fraction = 0.9f),
                onValueChange = { email = it }
            )

            OutlinedTextField(
                value = senha,
                label = { Text(text = "Digite sua senha") },
                modifier = modifier.fillMaxWidth(fraction = 0.9f),
                onValueChange = { senha = it },
                visualTransformation = PasswordVisualTransformation()
            )

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (email == "" || senha == "") {
                        Toast.makeText(activity, "Preencha todos os campos", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Firebase.auth.signInWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(activity!!) { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        activity,
                                        "Usuário autenticado!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    activity?.startActivity(
                                        Intent(activity, MainActivity::class.java).setFlags(
                                            FLAG_ACTIVITY_SINGLE_TOP
                                        )
                                    )
                                } else {
                                    Toast.makeText(activity, "Login FALHOU!", Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                    }
                }
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text("Entrar")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    Toast.makeText(activity, "Página de cadastro!", Toast.LENGTH_LONG).show()
                    activity?.startActivity(
                        Intent(activity, CadastroActivity::class.java).setFlags(
                            FLAG_ACTIVITY_SINGLE_TOP
                        )
                    )
                }
            ) {
                Text("Cadastrar")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    Toast.makeText(activity, "Usuário anônimo!", Toast.LENGTH_LONG).show()
                    activity?.startActivity(
                        Intent(activity, MainActivity::class.java).setFlags(
                            FLAG_ACTIVITY_SINGLE_TOP
                        )
                    )
                }
            ) {
                Text("Entrar anonimamente")
            }
        }
    }
}