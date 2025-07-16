package com.example.aguaparatodos

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aguaparatodos.ui.theme.AguaParaTodosTheme

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
    val snackbarHostState = remember { SnackbarHostState() }
    val activity = LocalContext.current as? Activity

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var repeat_password by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Cadastro de Usuário", fontSize = 24.sp)

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth(0.9f).padding(top = 16.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth(0.9f).padding(top = 8.dp)
            )

            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                label = { Text("Digite sua senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.9f).padding(top = 8.dp)
            )

            OutlinedTextField(
                value = repeat_password,
                label = { Text(text = "Digite sua senha novamente") },
                onValueChange = { repeat_password = it },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.9f).padding(top = 8.dp)
            )

            Button(
                onClick = { Toast.makeText(activity, "Cadastrado com Sucesso!",
                    Toast.LENGTH_LONG).show() },
                modifier = Modifier.padding(top = 24.dp).fillMaxWidth(0.9f)
            ) {
                Text("Cadastrar")
            }

            Button(
                onClick = { nome = ""; email = ""; senha = ""; repeat_password = "";
                    Toast.makeText(activity, "Você limpou todos os campos!", Toast.LENGTH_LONG).show() },
                enabled = nome.isNotEmpty() || email.isNotEmpty() || senha.isNotEmpty() || senha.isNotEmpty(),
                modifier = Modifier.padding(top = 24.dp).fillMaxWidth(0.9f),
            ) {
                Text("Limpar")
            }
        }
    }
}
