package com.example.tpinterfaces.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.material.icons.outlined.Phone
import com.example.tpinterfaces.ui.theme.ButtonBackgraundApp

private val ColorGreen   = Color(0xFF3A5B3D)
private val ColorYellow  = Color(0xFFF8C453)
private val ColorSurface = Color(0xFFF5F5F2)
private val ColorCard    = Color(0xFFFFFFFF)
private val ColorText    = Color(0xFF1A1A1A)
private val ColorSubtext = Color(0xFF6B7B6E)

private val localeEs      = Locale("es", "AR")
private val dateFormatter = DateTimeFormatter.ofPattern("d 'de' MMMM, yyyy", localeEs)
private val dateFormShort = DateTimeFormatter.ofPattern("d 'de' MMMM, yyyy", localeEs)
private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm 'hs'")

@Composable
fun TurnosScreen(
    modifier: Modifier = Modifier,
    onSolicitarTurno: () -> Unit = {}
) {
    val proximos  = TurnosRepository.proximos
    val historial = TurnosRepository.historial

    Surface(
        modifier = modifier.fillMaxSize(),
        color    = ColorSurface
    ) {
        LazyColumn(
            contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(bottom = 4.dp)) {
                    Text(
                        text       = "Turnos",
                        fontSize   = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color      = ColorText
                    )
                    Text(
                        text     = "Gestioná tus citas municipales",
                        fontSize = 14.sp,
                        color    = ColorSubtext
                    )
                }
            }

            item {
                Button(
                    onClick  = onSolicitarTurno,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape    = RoundedCornerShape(14.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = ButtonBackgraundApp)
                ) {
                    Text(
                        text       = "+ Solicitar nuevo turno",
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = Color.White
                    )
                }
            }

            item {
                SectionHeader(title = "Mis turnos")
            }

            if (proximos.isEmpty()) {
                item {
                    Text(
                        text     = "No tenés turnos próximos.",
                        fontSize = 14.sp,
                        color    = ColorSubtext,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            } else {
                items(items = proximos) { turno: Turno ->
                    TurnoCard(turno = turno)
                }
            }

            item {
                SectionHeader(title = "Historial", topPadding = 8.dp)
            }

            items(items = historial) { turno: Turno ->
                HistorialCard(turno = turno)
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, topPadding: Dp = 0.dp) {
    Text(
        text       = title,
        fontSize   = 18.sp,
        fontWeight = FontWeight.Bold,
        color      = ColorText,
        modifier   = Modifier.padding(top = topPadding, bottom = 2.dp)
    )
}

@Composable
private fun TurnoCard(turno: Turno) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = ColorCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.Top
            ) {
                Text(
                    text       = turno.titulo,
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = ColorText,
                    //modifier   = Modifier.weight(1f)
                )
                //Spacer(Modifier.width(8.dp))
                //EstadoBadge(estado = turno.estado)
            }
            Spacer(Modifier.height(6.dp))
            Text(
                text       = turno.mascota,
                fontSize   = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color      = ColorGreen
            )
            Spacer(Modifier.height(10.dp))
            InfoRow(icon = Icons.Outlined.CalendarMonth, text = turno.fecha.format(dateFormatter))
            Spacer(Modifier.height(4.dp))
            InfoRow(icon = Icons.Outlined.Schedule, text = turno.hora.format(timeFormatter))
            Spacer(Modifier.height(4.dp))
            InfoRow(icon = Icons.Outlined.LocationOn, text = turno.lugar)
            Spacer(Modifier.height(12.dp))

            HorizontalDivider(color = Color(0xFFF5F5F2))

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = ColorGreen.copy(alpha = 0.12f)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = ButtonBackgraundApp,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Cómo llegar", color = ButtonBackgraundApp, fontSize = 13.sp)
                }
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonBackgraundApp),
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

@Composable
private fun HistorialCard(turno: Turno) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = ColorCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text       = turno.titulo,
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = ColorSubtext
                )
                Text(
                    text     = "${turno.mascota} · ${turno.fecha.format(dateFormShort)}",
                    fontSize = 13.sp,
                    color    = ColorSubtext.copy(alpha = 0.7f)
                )
            }
            Text(
                text       = "Completado",
                fontSize   = 13.sp,
                fontWeight = FontWeight.Medium,
                color      = ColorSubtext
            )
        }
    }
}

@Composable
private fun EstadoBadge(estado: EstadoTurno) {
    val (label, bgColor, textColor) = when (estado) {
        EstadoTurno.CONFIRMADO -> Triple("Confirmado", ColorGreen.copy(alpha = 0.12f), ColorGreen)
        EstadoTurno.PENDIENTE  -> Triple("Pendiente",  ColorYellow.copy(alpha = 0.25f), Color(0xFFB8860B))
        EstadoTurno.COMPLETADO -> Triple("Completado", ColorSubtext.copy(alpha = 0.12f), ColorSubtext)
    }
    Box(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text       = label,
            fontSize   = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color      = textColor
        )
    }
}

@Composable
private fun InfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector        = icon,
            contentDescription = null,
            tint               = ColorGreen,
            modifier           = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text     = text,
            fontSize = 13.sp,
            color    = ColorSubtext
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 820)
@Composable
private fun TurnosScreenPreview() {
    MaterialTheme {
        TurnosScreen()
    }
}