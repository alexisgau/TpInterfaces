package com.example.tpinterfaces.data.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class QuickAction(
    val id: String,
    val titulo: String,
    @DrawableRes val iconoRes: Int,
    val colorFondo: Color,
    val colorIcono: Color
)

data class Campania(
    val id: String,
    val titulo: String,
    val subtitulo: String,
    val imagen: Int,
    val activa: Boolean = true
)

data class Recordatorio(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val fechaLimite: String,
    val imageVector: ImageVector
)

data class Noticia(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val imagenUrl: String
)

data class Usuario(
    val nombre: String,
    val fotoUrl: String?
)