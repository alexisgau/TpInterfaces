package com.example.tpinterfaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tpinterfaces.branding.BrandCatalog
import com.example.tpinterfaces.branding.BrandConfig
import com.example.tpinterfaces.ui.AppDestinations
import com.example.tpinterfaces.ui.AppNavigation
import com.example.tpinterfaces.ui.Screen
import com.example.tpinterfaces.ui.theme.TpInterfacesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TpInterfacesTheme(brand = BrandCatalog.MORON) {
                TpInterfacesApp()
            }
        }
    }
}

@Composable
fun TpInterfacesApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val mostrarNavBar = currentDestination?.hasRoute(Screen.SolicitarTurno::class) != true

    val currentTab = AppDestinations.entries.find { tab ->
        currentDestination?.hasRoute(tab.route::class) == true
    } ?: AppDestinations.INICIO

    val colorSeleccionado = Color(0xFF4E8752)
    val colorInactivo     = Color(0xFF9E9E9E)

    val coloresDeLaBarra = NavigationSuiteDefaults.itemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor   = colorSeleccionado,
            selectedTextColor   = colorSeleccionado,
            unselectedIconColor = colorInactivo,
            unselectedTextColor = colorInactivo,
            indicatorColor      = Color.Transparent
        )
    )

    if (mostrarNavBar) {
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                AppDestinations.entries.forEach { tab ->
                    item(
                        icon     = { Icon(tab.icon, contentDescription = tab.label) },
                        label    = { Text(tab.label) },
                        selected = tab == currentTab,
                        onClick  = {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState    = true
                            }
                        },
                        colors = coloresDeLaBarra
                    )
                }
            }
        ) {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                AppNavigation(
                    navController = navController,
                    modifier      = Modifier.padding(innerPadding)
                )
            }
        }
    } else {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppNavigation(
                navController = navController,
                modifier      = Modifier.padding(innerPadding)
            )
        }
    }
}