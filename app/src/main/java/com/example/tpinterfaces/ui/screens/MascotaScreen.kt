package com.example.tpinterfaces.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tpinterfaces.data.repository.MascotasRepositoryImpl
import com.example.tpinterfaces.ui.viewModel.MascotasViewModel
import com.example.tpinterfaces.ui.viewModel.MascotasViewModelFactory
import com.example.tpinterfaces.data.model.Mascota
import com.example.tpinterfaces.data.model.EstadoSalud
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tpinterfaces.R
import com.example.tpinterfaces.ui.theme.BackgroundApp
import com.example.tpinterfaces.ui.theme.Green
import com.example.tpinterfaces.ui.theme.PurplePrimary
import com.example.tpinterfaces.ui.theme.Red
import com.example.tpinterfaces.ui.theme.Yellow

@Composable
fun MascotaScreen(
    onMascotaClick: (String) -> Unit,
    onAgregarClick: () -> Unit,
    viewModel: MascotasViewModel = viewModel(
        factory = MascotasViewModelFactory(
        MascotasRepositoryImpl()
        )
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(BackgroundApp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
                Text("Mis Mascotas", style = MaterialTheme.typography.titleLarge)
                Text(
                    "Gestioná la salud de tus compañeros",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            if (uiState.cargando) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.mascotas) { mascota ->
                        MascotaCard(
                            mascota = mascota,
                            onClick = { onMascotaClick(mascota.id) }
                        )
                    }
                    item { Spacer(Modifier.height(80.dp)) }
                }
            }
        }

        // FAB agregar mascota
        FloatingActionButton(
            onClick = onAgregarClick,
            modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp),
            containerColor = PurplePrimary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar mascota",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun MascotaCard(mascota: Mascota, onClick: () -> Unit) {
    val (estadoColor, estadoTexto, estadoIcono) =
        when (mascota.estadoSalud) {
        EstadoSalud.AL_DIA -> Triple(Green, "Al día", Icons.Default.Check)
        EstadoSalud.ALERTA -> Triple(Yellow, "Alerta", Icons.Default.Warning)
        EstadoSalud.VENCIDO -> Triple(Red, "Vencido", Icons.Default.Error)
    }

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Foto
                Image(
                    painter = painterResource(id = mascota.fotoRes ?: R.drawable.ic_avatar_defecto),
                    contentDescription = mascota.nombre,
                    modifier = Modifier.size(56.dp).clip(CircleShape).background(Color(0xFFE8F5EC)),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(12.dp))

                // Info
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = mascota.nombre,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        "${mascota.especie.label} · ${mascota.raza} · ${mascota.edadAnios} años",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )
                }

                // Estado badge
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(estadoColor.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = estadoIcono,
                        contentDescription = null,
                        tint = estadoColor,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = estadoTexto,
                        color = estadoColor,
                        style = MaterialTheme.typography.titleSmall)
                }
            }

            // Alertas
            if (mascota.alertas.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                HorizontalDivider(color = Color(0xFFF0F0F0))
                Spacer(Modifier.height(8.dp))
                mascota.alertas.forEach { alerta ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = Color(0xFFE8A100),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(alerta, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF555555))
                    }
                }
            }
        }
    }
}