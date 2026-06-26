package com.example.tpinterfaces.ui


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.Place
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
    val route: Screen
) {
    INICIO("Inicio", Icons.Outlined.Home, Screen.Inicio),
    MASCOTAS("Mascotas", Icons.Outlined.Pets, Screen.Mascotas),
    TURNOS("Turnos", Icons.Outlined.CalendarMonth, Screen.Turnos),
    SERVICIOS("Servicios", Icons.Outlined.Place, Screen.Servicios),
    PERFIL("Perfil", Icons.Outlined.Person, Screen.Perfil),
}