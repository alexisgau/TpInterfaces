package com.example.tpinterfaces.ui

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable data object Inicio          : Screen()
    @Serializable data object Mascotas        : Screen()
    @Serializable data object Turnos          : Screen()
    @Serializable data object SolicitarTurno  : Screen()
    @Serializable data object Servicios       : Screen()
    @Serializable data object Perfil          : Screen()
    @Serializable data object DatosPersonales : Screen()
    @Serializable data class DetalleMascota(val mascotaId: String)    : Screen()
    @Serializable data object AgregarMascota  : Screen()
}
