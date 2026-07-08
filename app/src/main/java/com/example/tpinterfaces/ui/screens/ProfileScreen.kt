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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.tpinterfaces.R
import androidx.compose.foundation.clickable
import com.example.tpinterfaces.ui.theme.TpInterfacesTheme
import com.example.tpinterfaces.branding.BrandCatalog

//@Preview(showBackground = true, widthDp = 390, heightDp = 820)
//@Composable
//fun ProfileScreenPreview() {
//    TpInterfacesTheme(brand = BrandCatalog.MORON) {
//        ProfileScreen()
//    }
//}
@Preview(showBackground = true, widthDp = 390, heightDp = 820)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen()
    }
}

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
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onIrATurnos: () -> Unit = {},
    onIrAMascotas: () -> Unit = {},
    onIrADatosPersonales: () -> Unit = {}
) {
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
                            .background(Color(0xFFD0D0D0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "CG",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp
                        )
                    }
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

        // Card destacada: Recordatorio Rocky
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FondoCard),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onIrATurnos() },
            border = androidx.compose.foundation.BorderStroke(3.dp, Amarillo)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(AmarilloSuave),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.golden_rocky),
                        contentDescription = "Rocky",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Recordatorio de turno", fontWeight = FontWeight.Bold, fontSize = 16.sp)
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
                MenuRow(
                    icon = Icons.Outlined.Person,
                    iconColor = Verde,
                    label = "Datos personales",
                    onClick = onIrADatosPersonales
                )
                HorizontalDivider(color = FondoPantalla)
                MenuRow(
                    icon = Icons.Outlined.Pets,
                    iconColor = Verde,
                    label = "Mis mascotas",
                    onClick = onIrAMascotas
                )
                HorizontalDivider(color = FondoPantalla)
                MenuRow(
                    icon = Icons.Outlined.CalendarMonth,
                    iconColor = Verde,
                    label = "Mis turnos",
                    onClick = onIrATurnos
                )
                HorizontalDivider(color = FondoPantalla)
                MenuRow(icon = Icons.Outlined.FavoriteBorder, iconColor = Verde, label = "Mis solicitudes de adopción")
                HorizontalDivider(color = FondoPantalla)
                MenuRow(icon = Icons.Outlined.VolunteerActivism, iconColor = Verde, label = "Hogar de tránsito")
                HorizontalDivider(color = FondoPantalla)
                MenuRow(icon = Icons.Outlined.Shield, iconColor = Verde, label = "Ayuda y soporte")
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
                Icon(Icons.AutoMirrored.Outlined.ExitToApp, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar sesión", color = Color.Black, fontSize = 15.sp)
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
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
    label: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
        Text(label, modifier = Modifier.weight(1f), fontSize = 15.sp)
        Icon(Icons.AutoMirrored.Outlined.KeyboardArrowRight, contentDescription = null, tint = Gris)
    }
}
