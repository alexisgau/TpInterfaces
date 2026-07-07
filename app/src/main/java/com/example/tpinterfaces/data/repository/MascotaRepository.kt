package com.example.tpinterfaces.data.repository

import com.example.tpinterfaces.data.model.Mascota

interface MascotasRepository {
    suspend fun obtenerMascotas(): List<Mascota>
    suspend fun obtenerMascota(id: String): Mascota?
    suspend fun agregarMascota(mascota: Mascota): Boolean
}