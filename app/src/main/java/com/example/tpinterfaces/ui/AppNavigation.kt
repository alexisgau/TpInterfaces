package com.example.tpinterfaces.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.tpinterfaces.ui.screens.AgregarMascotaScreen
import com.example.tpinterfaces.ui.screens.AyudaSoporteScreen
import com.example.tpinterfaces.ui.screens.DatosPersonalesScreen
import com.example.tpinterfaces.ui.screens.DetalleMascotaScreen
import com.example.tpinterfaces.ui.screens.HomeScreen
import com.example.tpinterfaces.ui.screens.LoginScreen
import com.example.tpinterfaces.ui.screens.MascotaScreen
import com.example.tpinterfaces.ui.screens.ProximasCampaniasScreen
import com.example.tpinterfaces.ui.screens.ProfileScreen
import com.example.tpinterfaces.ui.screens.RegistroScreen
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
        startDestination = Screen.Login,
        modifier         = modifier
    ) {
        composable<Screen.Login> {
            LoginScreen(
                onLoginExitoso = {
                    navController.navigate(Screen.Inicio) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                },
                onIrARegistro = { navController.navigate(Screen.Registro) }
            )
        }

        composable<Screen.Registro> {
            RegistroScreen(
                onBack = { navController.popBackStack() },
                onRegistroExitoso = {
                    navController.navigate(Screen.Inicio) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                }
            )
        }
        composable<Screen.Inicio>    {
            HomeScreen(
                onSolicitarTurno = { navController.navigate(Screen.SolicitarTurno) },
                onProximasCampanias = { navController.navigate(Screen.ProximasCampanias) }
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
                },
                onIrADatosPersonales = {
                    navController.navigate(Screen.DatosPersonales)
                },
                onIrAAyudaSoporte = {
                    navController.navigate(Screen.AyudaSoporte)
                }
            )
        }

        composable<Screen.DatosPersonales> {
            DatosPersonalesScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable<Screen.AyudaSoporte> {
            AyudaSoporteScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable<Screen.ProximasCampanias> {
            ProximasCampaniasScreen(
                onBack = { navController.popBackStack() }
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
