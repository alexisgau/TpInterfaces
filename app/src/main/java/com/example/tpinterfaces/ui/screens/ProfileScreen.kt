package com.example.tpinterfaces.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colores
private val Verde = Color(0xFF4E8752)
private val VerdeSuave = Color(0xFFEEF5EE)
private val MoradoSuave = Color(0xFFEDE9F6)
private val Morado = Color(0xFF7C6FCD)
private val AmarilloSuave = Color(0xFFFFF8EC)
private val Amarillo = Color(0xFFF5A623)
private val Gris = Color(0xFF9E9E9E)
private val FondoPantalla = Color(0xFFF5F5F0)
private val FondoCard = Color.White

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FondoPantalla)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header: foto + nombre + email
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FondoCard),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Gris)
                    )
                    Column {
                        Text(
                            text = "Cristina González",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Verde
                        )
                        Text(
                            text = "cristina.gonzalez@email.com",
                            fontSize = 14.sp,
                            color = Gris
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Stats: Mascotas, Turnos, Adopciones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatCard(
                        icon = Icons.Outlined.Pets,
                        iconBackground = VerdeSuave,
                        iconColor = Verde,
                        number = "3",
                        label = "Mascotas",
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        icon = Icons.Outlined.CalendarMonth,
                        iconBackground = MoradoSuave,
                        iconColor = Morado,
                        number = "2",
                        label = "Turnos",
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        icon = Icons.Outlined.FavoriteBorder,
                        iconBackground = AmarilloSuave,
                        iconColor = Amarillo,
                        number = "1",
                        label = "Adopciones",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
/*
        // Card destacada: Toby necesita tránsito
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FondoCard),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MoradoSuave),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.Pets, contentDescription = null, tint = Morado, modifier = Modifier.size(28.dp))
                }
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Toby necesita tránsito", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Edad: 4 meses  ·  15 días  ·  3 km", fontSize = 13.sp, color = Gris)
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(MoradoSuave)
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Ver detalle", color = Morado, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            Icon(Icons.AutoMirrored.Outlined.KeyboardArrowRight, contentDescription = null, tint = Morado, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }*/

        // Card destacada: Recordatorio Rocky
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FondoCard),
            modifier = Modifier.fillMaxWidth(),
            border = androidx.compose.foundation.BorderStroke(3.dp, Amarillo)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(AmarilloSuave),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🐶", fontSize = 28.sp)
                }
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Recordatorio", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(
                        "Rocky necesita vacuna antirrábica en 10 días.",
                        fontSize = 13.sp,
                        color = Gris
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Outlined.Schedule, contentDescription = null, tint = Amarillo, modifier = Modifier.size(14.dp))
                        Text("Vence el 15 de julio", color = Amarillo, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }
                }
                Icon(Icons.AutoMirrored.Outlined.KeyboardArrowRight, contentDescription = null, tint = Gris)
            }
        }

        // Sección Configuración
        Text("Configuración", fontWeight = FontWeight.Bold, fontSize = 16.sp)

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FondoCard),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                MenuRow(icon = Icons.Outlined.Person, iconColor = Verde, label = "Datos personales")
                HorizontalDivider(color = FondoPantalla)
                MenuRow(icon = Icons.Outlined.Pets, iconColor = Verde, label = "Mis mascotas")
                HorizontalDivider(color = FondoPantalla)
                MenuRow(icon = Icons.Outlined.CalendarMonth, iconColor = Verde, label = "Mis turnos")
                HorizontalDivider(color = FondoPantalla)
                MenuRow(icon = Icons.Outlined.FavoriteBorder, iconColor = Color(0xFFE57373), label = "Mis solicitudes de adopción")
                HorizontalDivider(color = FondoPantalla)
                MenuRow(icon = Icons.Outlined.VolunteerActivism, iconColor = Morado, label = "Hogar de tránsito")
                HorizontalDivider(color = FondoPantalla)
                MenuRow(icon = Icons.Outlined.Notifications, iconColor = Amarillo, label = "Notificaciones")
                HorizontalDivider(color = FondoPantalla)
                MenuRow(icon = Icons.Outlined.Settings, iconColor = Gris, label = "Configuración")
                HorizontalDivider(color = FondoPantalla)
                MenuRow(icon = Icons.Outlined.Shield, iconColor = Color(0xFF42A5F5), label = "Ayuda y soporte")
            }
        }

        // Botón Cerrar sesión
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FondoCard),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.AutoMirrored.Outlined.ExitToApp, contentDescription = null, tint = Gris)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar sesión", color = Gris, fontSize = 15.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun StatCard(
    icon: ImageVector,
    iconBackground: Color,
    iconColor: Color,
    number: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = FondoPantalla),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Text(number, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(label, fontSize = 12.sp, color = Gris)
        }
    }
}

@Composable
private fun MenuRow(
    icon: ImageVector,
    iconColor: Color,
    label: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
        Text(label, modifier = Modifier.weight(1f), fontSize = 15.sp)
        Icon(Icons.AutoMirrored.Outlined.KeyboardArrowRight, contentDescription = null, tint = Gris)
    }
}