package com.example.tpinterfaces.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tpinterfaces.R
import com.example.tpinterfaces.ui.theme.BackgroundApp
import com.example.tpinterfaces.ui.theme.ButtonBackgraundApp
import com.example.tpinterfaces.ui.theme.GreenLight
import com.example.tpinterfaces.ui.theme.RedHeart
import com.example.tpinterfaces.ui.theme.TextSecondary
import com.example.tpinterfaces.ui.theme.YellowSoft

private data class ProximaCampania(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val horario: String,
    val lugar: String,
    val imagenDrawableName: String
)

private val campaniasHarcodeadas = listOf(
    ProximaCampania(
        id = "vacunacion_antirrabica",
        titulo = "Vacunación Antirrábica Gratuita",
        descripcion = "Vacuná a tu perro o gato sin costo. No necesitás turno previo.",
        fecha = "15 - 30 de julio",
        horario = "Lun a Vie 9:00 - 16:00",
        lugar = "Centro Municipal de Salud Animal",
        imagenDrawableName = "campania_vacunacion_antirrabica"
    ),
    ProximaCampania(
        id = "castracion",
        titulo = "Campaña de Castración",
        descripcion = "Cirugías gratuitas para perros y gatos. Solicitá turno desde la app.",
        fecha = "Todos los martes",
        horario = "8:00 - 14:00",
        lugar = "Veterinaria Comunitaria Norte",
        imagenDrawableName = "campania_castracion"
    ),
    ProximaCampania(
        id = "tenencia_responsable",
        titulo = "Tenencia Responsable",
        descripcion = "Charlas gratuitas sobre cuidado de mascotas y bienestar animal.",
        fecha = "Sábado 20 de julio",
        horario = "10:00 - 12:00",
        lugar = "Plaza Principal",
        imagenDrawableName = "campania_tenencia_responsable"
    )
)

@Composable
fun ProximasCampaniasScreen(
    onBack: () -> Unit
) {
    var favoritas by rememberSaveable { mutableStateOf(emptyList<String>()) }
    var mostrarSoloFavoritas by rememberSaveable { mutableStateOf(false) }
    val campaniasVisibles = if (mostrarSoloFavoritas) {
        campaniasHarcodeadas.filter { it.id in favoritas }
    } else {
        campaniasHarcodeadas
    }

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
                text = "Próximas campañas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(12.dp))

        ReminderInfoCard()

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { mostrarSoloFavoritas = !mostrarSoloFavoritas },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (mostrarSoloFavoritas) ButtonBackgraundApp else GreenLight,
                contentColor = if (mostrarSoloFavoritas) Color.White else ButtonBackgraundApp
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = "Mis campañas favoritas (${favoritas.size})",
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(16.dp))

        if (campaniasVisibles.isEmpty()) {
            EmptyFavoritesCard()
        } else {
            campaniasVisibles.forEach { campania ->
                CampaniaCard(
                    campania = campania,
                    favorita = campania.id in favoritas,
                    onFavoriteClick = {
                        favoritas = if (campania.id in favoritas) {
                            favoritas - campania.id
                        } else {
                            favoritas + campania.id
                        }
                    }
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ReminderInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = YellowSoft),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = ButtonBackgraundApp
                )
            }
            Text(
                text = "Las campañas que marques como favoritas te enviarán un recordatorio por notificaciones unos días antes de la fecha estipulada.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF5D5A3B)
            )
        }
    }
}

@Composable
private fun EmptyFavoritesCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Text(
            text = "Todavía no marcaste campañas como favoritas.",
            modifier = Modifier.padding(18.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary
        )
    }
}

@Composable
private fun CampaniaCard(
    campania: ProximaCampania,
    favorita: Boolean,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                CampaignImage(
                    drawableName = campania.imagenDrawableName,
                    contentDescription = campania.titulo
                )
                Text(
                    text = "Activa",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.White)
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = ButtonBackgraundApp
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.92f))
                        .clickable(onClick = onFavoriteClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (favorita) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (favorita) "Quitar de favoritas" else "Agregar a favoritas",
                        tint = if (favorita) RedHeart else RedHeart.copy(alpha = 0.68f),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = campania.titulo,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )
                Text(
                    text = campania.descripcion,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary
                )
                CampaignInfoRow(
                    icon = Icons.Outlined.CalendarMonth,
                    text = "${campania.fecha}  ·  ${campania.horario}"
                )
                CampaignInfoRow(
                    icon = Icons.Outlined.Place,
                    text = campania.lugar
                )
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenLight,
                        contentColor = ButtonBackgraundApp
                    )
                ) {
                    Text(
                        text = "Más información",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.size(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun CampaignImage(
    drawableName: String,
    contentDescription: String
) {
    val context = LocalContext.current
    val drawableId = remember(drawableName) {
        context.resources.getIdentifier(drawableName, "drawable", context.packageName)
    }

    if (drawableId != 0) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            GreenLight,
                            Color(0xFFFCE4EC)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_campaign),
                contentDescription = contentDescription,
                tint = ButtonBackgraundApp.copy(alpha = 0.55f),
                modifier = Modifier.size(56.dp)
            )
        }
    }
}

@Composable
private fun CampaignInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = ButtonBackgraundApp,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 820)
@Composable
private fun ProximasCampaniasScreenPreview() {
    MaterialTheme {
        ProximasCampaniasScreen(onBack = {})
    }
}
