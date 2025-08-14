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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
@Preview(showBackground = true)
@Composable
fun LoginPage(modifier: Modifier = Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    var senha by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity
    var isLoading by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var senhaError by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo do aplicativo",
                modifier = Modifier
                    .size(160.dp)
                    .padding(top = 16.dp),
                contentScale = ContentScale.Fit
            )
            Text(text = "Login", fontSize = 24.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.secondary)
            OutlinedTextField(
                value = email,
                label = { Text(text = "E-mail") },
                modifier = Modifier.fillMaxWidth(),
                isError = emailError != null,
                supportingText = { Text((if (emailError == null) "" else emailError).toString()) },
                onValueChange = {
                    email = it
                    emailError = null
                },
            )

            OutlinedTextField(
                value = senha,
                label = { Text(text = "Senha") },
                modifier = Modifier.fillMaxWidth(),
                isError = senhaError != null,
                supportingText = { Text((if (senhaError == null) "" else senhaError).toString()) },
                onValueChange = {
                    senha = it
                    senhaError = null
                },
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                onClick = {
                    var valid = true
                    val requiredField = "Campo obrigatório"

                    if (email.isBlank()) {
                        emailError = requiredField
                        valid = false
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emailError = "E-mail inválido"
                        valid = false
                    }

                    if (senha.isBlank()) {
                        senhaError = requiredField
                        valid = false
                    } else if (senha.length < 6) {
                        senhaError = "Mínimo de 6 caracteres"
                        valid = false
                    }

                    if (valid) {
                        isLoading = true
                        Firebase.auth.signInWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(activity!!) { task ->
                                isLoading = false

                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        activity,
                                        "Usuário autenticado!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    activity.startActivity(
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
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text("Entrar")
                }
            }
        }
        TextButton (
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
            onClick = {
                activity?.startActivity(
                    Intent(activity, CadastroActivity::class.java).setFlags(
                        FLAG_ACTIVITY_SINGLE_TOP
                    )
                )
            }
        ) {
            Text("Cadastrar")
        }
    }
}