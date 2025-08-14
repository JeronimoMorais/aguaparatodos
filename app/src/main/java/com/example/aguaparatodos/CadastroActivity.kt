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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguaparatodos.db.FBDatabase
import com.example.aguaparatodos.db.toFBUser
import com.example.aguaparatodos.models.User
import com.example.aguaparatodos.ui.theme.AguaParaTodosTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class CadastroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AguaParaTodosTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CadastroPage(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Preview(showBackground = true)
@Composable
fun CadastroPage(modifier: Modifier = Modifier) {
    val activity = LocalContext.current as? Activity

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var repeat_password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var nomeError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var senhaError by remember { mutableStateOf<String?>(null) }
    var repeteSenhaError by remember { mutableStateOf<String?>(null) }

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
            Text("Cadastro de Usuário", fontSize = 24.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.secondary)

            OutlinedTextField(
                value = nome,
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth(),
                isError = nomeError != null,
                supportingText = { Text((if (nomeError == null) "" else nomeError).toString()) },
                onValueChange = {
                    nome = it
                    nomeError = null
                },
            )

            OutlinedTextField(
                value = email,
                label = { Text("E-mail") },
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
                label = { Text("Digite sua senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = senhaError != null,
                supportingText = { Text((if (senhaError == null) "" else senhaError).toString()) },
                onValueChange = {
                    senha = it
                    senhaError = null
                },
            )

            OutlinedTextField(
                value = repeat_password,
                label = { Text(text = "Digite sua senha novamente") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = repeteSenhaError != null,
                supportingText = { Text((if (repeteSenhaError == null) "" else repeteSenhaError).toString()) },
                onValueChange = {
                    repeat_password = it
                    repeteSenhaError = null
                },
            )

            Button(
                onClick = {
                    var valid = true
                    val requiredField = "Campo obrigatório"

                    if (nome.isBlank()) {
                        nomeError = requiredField
                        valid = false
                    }

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

                    if (repeat_password.isBlank()) {
                        repeteSenhaError = requiredField
                        valid = false
                    } else if (!repeat_password.contentEquals(senha)) {
                        repeteSenhaError = "Senhas são diferenets"
                        valid = false
                    }

                    if (valid) {
                        isLoading = true
                        Firebase.auth.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(activity!!) { task ->
                                isLoading = false
                                if (task.isSuccessful) {
                                    FBDatabase().register(User(name = nome, email = email).toFBUser())
                                    Toast.makeText(activity,
                                        "Conta criada com sucesso!", Toast.LENGTH_LONG).show()
                                    activity.finish()
                                } else {
                                    Toast.makeText(activity,
                                        "Erro ao criar conta!", Toast.LENGTH_LONG).show()
                                }
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text("Cadastrar")
                }
            }
        }
        TextButton (
            onClick = { activity?.finish() },
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
        ) {
            Text("Voltar")
        }
    }
}
