package com.example.tpinterfaces.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.tpinterfaces.ui.screens.AgregarMascotaScreen
import com.example.tpinterfaces.ui.screens.DetalleMascotaScreen
import com.example.tpinterfaces.ui.screens.HomeScreen
import com.example.tpinterfaces.ui.screens.MascotaScreen
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
        composable<Screen.Inicio>    {
            HomeScreen(
                onSolicitarTurno = { navController.navigate(Screen.SolicitarTurno) }
            )
        }
        composable<Screen.Mascotas>  {
            MascotaScreen(
                onMascotaClick = { id -> navController.navigate(Screen.DetalleMascota(mascotaId = id)) },
                onAgregarClick = { navController.navigate(Screen.AgregarMascota) })
        }
        composable<Screen.Servicios> { ServiciosScreen() }
        composable<Screen.Perfil> {
            ProfileScreen(
                onIrATurnos = {
                    navController.navigate(Screen.Turnos) {
                        popUpTo(Screen.Inicio) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onIrAMascotas = {
                    navController.navigate(Screen.Mascotas) {
                        popUpTo(Screen.Inicio) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

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
        composable<Screen.DetalleMascota> { backStackEntry ->
            val detalleArgs = backStackEntry.toRoute<Screen.DetalleMascota>()

            DetalleMascotaScreen(
                mascotaId = detalleArgs.mascotaId,
                onBack = { navController.popBackStack() }
            )
        }
        composable<Screen.AgregarMascota> {
            AgregarMascotaScreen(
                onBack = { navController.popBackStack() },
                onGuardado = { navController.popBackStack() }
            )
        }
    }
}