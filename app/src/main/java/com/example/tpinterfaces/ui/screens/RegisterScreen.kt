package com.example.tpinterfaces.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Badge
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Phone


@Composable
fun RegistroScreen(
    onBack: () -> Unit,
    onRegistroExitoso: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var dni by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundApp)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        IconButton(onClick = onBack) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Crear cuenta",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = ButtonBackgraundApp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Completá tus datos para empezar",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it; error = null },
            placeholder = { Text("Nombre completo") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = camposColores(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; error = null },
            placeholder = { Text("Correo electrónico") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = camposColores(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = dni,
            onValueChange = { nuevoValor ->
                if (nuevoValor.length <= 8 && nuevoValor.all { it.isDigit() }) {
                    dni = nuevoValor
                    error = null
                }
            },
            placeholder = { Text("DNI") },
            leadingIcon = { Icon(Icons.Filled.Badge, contentDescription = null) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(16.dp),
            colors = camposColores(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = telefono,
            onValueChange = { nuevoValor ->
                if (nuevoValor.length <= 10 && nuevoValor.all { it.isDigit() }) {
                    telefono = nuevoValor
                    error = null
                }
            },
            placeholder = { Text("Teléfono") },
            leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = null) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            shape = RoundedCornerShape(16.dp),
            colors = camposColores(),
            modifier = Modifier.fillMaxWidth()
        )




        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it; error = null },
            placeholder = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(16.dp),
            colors = camposColores(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmarPassword,
            onValueChange = { confirmarPassword = it; error = null },
            placeholder = { Text("Confirmar contraseña") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(16.dp),
            colors = camposColores(),
            modifier = Modifier.fillMaxWidth()
        )

        if (error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(error ?: "", color = Color.Red, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                when {
                    nombre.isBlank() || email.isBlank() || password.isBlank() ->
                        error = "Completá todos los campos"
                    dni.length < 7 ->
                        error = "Ingresá un DNI válido"
                    telefono.length < 8 ->
                        error = "Ingresá un teléfono válido"
                    password != confirmarPassword ->
                        error = "Las contraseñas no coinciden"
                    else -> onRegistroExitoso()
                }
            },
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonBackgraundApp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Crear cuenta", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun camposColores() = OutlinedTextFieldDefaults.colors(
    unfocusedContainerColor = Color.White,
    focusedContainerColor = Color.White,
    unfocusedBorderColor = Color(0xFFE0E0E0),
    focusedBorderColor = ButtonBackgraundApp
)