package com.example.tpinterfaces.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import com.example.tpinterfaces.R
import androidx.compose.ui.graphics.vector.ImageVector

// Colores
private val Verde = Color(0xFF4E8752)
private val VerdeSuave = Color(0xFFEEF5EE)
private val FondoPantalla = Color(0xFFF5F5F0)
private val FondoCard = Color.White
private val Gris = Color(0xFF9E9E9E)
private val Amarillo = Color(0xFFF5A623)
private val VerdePastel = Color(0xFFB8D9BB)
private val MoradoPastel = Color(0xFFD4CCEF)
private val AmarilloPastel = Color(0xFFFFE4A0)

data class ServicioItem(
    val nombre: String,
    val tipo: String,
    val distancia: String,
    val direccion: String,
    val telefono: String,
    val horario: String,
    val rating: String,
    val tags: List<String>,
    val emoji: String,
    val colorFondo: Color
)

private val servicios = listOf(
    ServicioItem(
        nombre = "Centro Municipal de Sal...",
        tipo = "Veterinaria",
        distancia = "1.8 km",
        direccion = "Av. Rivadavia 5100, CABA",
        telefono = "011 4567-8900",
        horario = "Lun a Vie 8:00 - 18:00",
        rating = "4.8",
        tags = listOf("Vacunación", "Castración", "Consulta"),
        emoji = "🐾",
        colorFondo = VerdePastel
    ),
    ServicioItem(
        nombre = "Veterinaria Comunit ...",
        tipo = "Veterinaria",
        distancia = "2.5 km",
        direccion = "Av. Cabildo 2100, CABA",
        telefono = "011 4789-1234",
        horario = "Lun a Sab 9:00 - 20:00",
        rating = "4.5",
        tags = listOf("Urgencias", "Cirugía", "Diagnóstico"),
        emoji = "🐕",
        colorFondo = MoradoPastel
    ),
    ServicioItem(
        nombre = "Centro Vacunatorio S ...",
        tipo = "Vacunatorio",
        distancia = "4.1 km",
        direccion = "Av. Santa Fe 1500, CABA",
        telefono = "011 4321-5678",
        horario = "Lun a Dom 10:00 - 21:00",
        rating = "4.2",
        tags = listOf("Vacunas", "Urgencias", "Farmacia"),
        emoji = "💉",
        colorFondo = AmarilloPastel
    )
)

private val categorias = listOf(
    "Centros municipales" to Icons.Outlined.AccountBalance,
    "Veterinarias privadas" to Icons.Outlined.LocalHospital,
    "Centros de castración" to Icons.Outlined.MedicalServices,
    "Centros de vacunación" to Icons.Outlined.Vaccines,
    "Centros de emergencia" to Icons.Outlined.Emergency
)

@Composable
fun ServiciosScreen(modifier: Modifier = Modifier) {
    var categoriaSeleccionada by remember { mutableStateOf("Centros municipales") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FondoPantalla)
            .verticalScroll(rememberScrollState())
    ) {
        // Header con fondo blanco
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(FondoCard)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Servicios Cercanos",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
            Text(
                "Centros veterinarios y servicios de la comunidad",
                fontSize = 14.sp,
                color = Gris
            )

            // Barra de búsqueda
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(FondoPantalla)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Outlined.Search, contentDescription = null, tint = Gris)
                Text("Buscar veterinarias, vacunatorios...", color = Gris, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            categorias.forEach { (nombre, icono) ->
                val seleccionado = nombre == categoriaSeleccionada
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .then(
                            if (seleccionado) Modifier.background(VerdeSuave) else Modifier
                        )
                        .clickable { categoriaSeleccionada = nombre }
                        .padding(vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (seleccionado) Verde else Color(0xFFE0E0E0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            icono,
                            contentDescription = nombre,
                            tint = if (seleccionado) Color.White else Gris,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        nombre,
                        fontSize = 11.sp,
                        color = if (seleccionado) Verde else Gris,
                        fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 2,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    if (seleccionado) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Verde)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mapa placeholder
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.map_placeholder),
                contentDescription = "Mapa",
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Marcadores simulados
            Box(
                modifier = Modifier
                    .offset(x = (-60).dp, y = (-20).dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(VerdePastel),
                contentAlignment = Alignment.Center
            ) {
                Text("1", color = Color.Black, fontWeight = FontWeight.Bold)
            }
            Box(
                modifier = Modifier
                    .offset(x = 50.dp, y = (-10).dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MoradoPastel),
                contentAlignment = Alignment.Center
            ) {
                Text("2", color = Color.Black, fontWeight = FontWeight.Bold)
            }
            Box(
                modifier = Modifier
                    .offset(x = (-10).dp, y = 30.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(AmarilloPastel),
                contentAlignment = Alignment.Center
            ) {
                Text("3", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Lista "Cerca tuyo"
        Text(
            "Cerca tuyo",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        servicios.forEach { servicio ->
            ServicioCard(servicio = servicio)
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun ServicioCard(servicio: ServicioItem) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = FondoCard),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header de la card
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(servicio.colorFondo),
                    contentAlignment = Alignment.Center
                ) {
                    Text(servicio.emoji, fontSize = 32.sp)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            servicio.nombre,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(Icons.Outlined.Star, contentDescription = null, tint = Amarillo, modifier = Modifier.size(16.dp))
                        Text(servicio.rating, fontSize = 14.sp, color = Amarillo, fontWeight = FontWeight.Bold)
                    }
                    Text(servicio.tipo, fontSize = 13.sp, color = Gris)
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(VerdeSuave)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(servicio.distancia, fontSize = 12.sp, color = Verde, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Dirección, teléfono, horario
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Outlined.Place, contentDescription = null, tint = Verde, modifier = Modifier.size(16.dp))
                Text(servicio.direccion, fontSize = 13.sp, color = Gris)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Outlined.Phone, contentDescription = null, tint = Verde, modifier = Modifier.size(16.dp))
                Text(servicio.telefono, fontSize = 13.sp, color = Gris)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Outlined.Schedule, contentDescription = null, tint = Verde, modifier = Modifier.size(16.dp))
                Text(servicio.horario, fontSize = 13.sp, color = Gris)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tags
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                servicio.tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(FondoPantalla)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(tag, fontSize = 12.sp, color = Color(0xFF555555))
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(color = FondoPantalla)

            Spacer(modifier = Modifier.height(12.dp))

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = VerdeSuave),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        Icons.Outlined.Place,
                        contentDescription = null,
                        tint = Verde,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Cómo llegar", color = Verde, fontSize = 13.sp)
                }
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Verde),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        Icons.Outlined.Phone,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Llamar", color = Color.White, fontSize = 13.sp)
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@androidx.compose.runtime.Composable
fun ServiciosScreenPreview() {
    com.example.tpinterfaces.ui.theme.TpInterfacesTheme {
        ServiciosScreen()
    }
}