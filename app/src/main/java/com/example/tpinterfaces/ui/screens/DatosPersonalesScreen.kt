package com.example.tpinterfaces.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tpinterfaces.ui.theme.BackgroundApp
import com.example.tpinterfaces.ui.theme.ButtonBackgraundApp
import com.example.tpinterfaces.ui.theme.GreenLight
import com.example.tpinterfaces.ui.theme.TextSecondary

@Composable
fun DatosPersonalesScreen(
    onBack: () -> Unit,
    onEditarPerfil: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundApp)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(bottom = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Volver"
                )
            }
            Text(
                text = "Datos personales",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 28.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(88.dp)
                            .clip(CircleShape)
                            .background(GreenLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "CG",
                            color = ButtonBackgraundApp,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Cristina González",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = ButtonBackgraundApp,
                    )
                    Text(
                        text = "Vecina registrada",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                DatoPersonalRow(
                    icon = Icons.Outlined.Person,
                    label = "Nombre completo",
                    value = "Cristina González"
                )
                HorizontalDivider(color = BackgroundApp)
                DatoPersonalRow(
                    icon = Icons.Outlined.Badge,
                    label = "DNI",
                    value = "28.456.789"
                )
                HorizontalDivider(color = BackgroundApp)
                DatoPersonalRow(
                    icon = Icons.Outlined.Email,
                    label = "Email",
                    value = "cristina.gonzalez@email.com"
                )
                HorizontalDivider(color = BackgroundApp)
                DatoPersonalRow(
                    icon = Icons.Outlined.Call,
                    label = "Teléfono",
                    value = "+54 11 5555-1234"
                )
                HorizontalDivider(color = BackgroundApp)
                DatoPersonalRow(
                    icon = Icons.Outlined.Home,
                    label = "Dirección",
                    value = "Av. Rivadavia 1234, Morón"
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onEditarPerfil,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonBackgraundApp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = "Editar perfil",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun DatoPersonalRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(GreenLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ButtonBackgraundApp,
                modifier = Modifier.size(22.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 820)
@Composable
private fun DatosPersonalesScreenPreview() {
    MaterialTheme {
        DatosPersonalesScreen(onBack = {})
    }
}
