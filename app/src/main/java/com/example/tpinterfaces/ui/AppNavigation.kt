package com.example.tpinterfaces.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tpinterfaces.ui.screens.HomeScreen
import com.example.tpinterfaces.ui.screens.MascotasScreen
import com.example.tpinterfaces.ui.screens.ProfileScreen
import com.example.tpinterfaces.ui.screens.ServiciosScreen
import com.example.tpinterfaces.ui.screens.SolicitarTurnoScreen
import com.example.tpinterfaces.ui.screens.TurnosScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController    = navController,
        startDestination = Screen.Inicio,
        modifier         = modifier
    ) {
        composable<Screen.Inicio>    { HomeScreen() }
        composable<Screen.Mascotas>  { MascotasScreen() }
        composable<Screen.Servicios> { ServiciosScreen() }
        composable<Screen.Perfil>    { ProfileScreen() }

        composable<Screen.Turnos> {
            TurnosScreen(
                onSolicitarTurno = { navController.navigate(Screen.SolicitarTurno) }
            )
        }

        composable<Screen.SolicitarTurno> {
            SolicitarTurnoScreen(
                onBack      = { navController.popBackStack() },
                onConfirmar = {
                    navController.navigate(Screen.Turnos) {
                        popUpTo(Screen.Turnos) { inclusive = true }
                    }
                }
            )
        }
    }
}