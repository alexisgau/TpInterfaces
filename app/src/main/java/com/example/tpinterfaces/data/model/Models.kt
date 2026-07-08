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
    val imagenUrl: String,
    val imagenRes: Int? = null
)

data class Usuario(
    val nombre: String,
    val fotoUrl: String?
)

// --- Mascotas ---

enum class EspecieMascota(val label: String) {
    PERRO("Perro"), GATO("Gato"), CONEJO("Conejo"),
    AVE("Ave"), OTRO("Otro")
}

enum class SexoMascota(val label: String) {
    MACHO("Macho"), HEMBRA("Hembra")
}

enum class EstadoSalud {
    AL_DIA, ALERTA, VENCIDO
}

data class Vacuna(
    val id: String,
    val nombre: String,
    val ultimaFecha: String,
    val proximaFecha: String,
    val estado: EstadoSalud
)

data class Tratamiento(
    val id: String,
    val nombre: String,
    val fecha: String,
    val estado: EstadoSalud
)

data class Mascota(
    val id: String,
    val nombre: String,
    val especie: EspecieMascota,
    val raza: String,
    val edadAnios: Int,
    val pesoKg: Double,
    val sexo: SexoMascota,
    val castrado: Boolean,
    @DrawableRes val fotoRes: Int?,
    val alergias: List<String> = emptyList(),
    val vacunas: List<Vacuna> = emptyList(),
    val tratamientos: List<Tratamiento> = emptyList(),
    val estadoSalud: EstadoSalud = EstadoSalud.AL_DIA,
    val alertas: List<String> = emptyList()
)