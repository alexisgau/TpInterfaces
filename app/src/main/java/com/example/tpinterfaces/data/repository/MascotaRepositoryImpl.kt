package com.example.tpinterfaces.data.repository

import com.example.tpinterfaces.R
import com.example.tpinterfaces.data.model.EspecieMascota
import com.example.tpinterfaces.data.model.EstadoSalud
import com.example.tpinterfaces.data.model.Mascota
import com.example.tpinterfaces.data.model.SexoMascota
import com.example.tpinterfaces.data.model.Tratamiento
import com.example.tpinterfaces.data.model.Vacuna


class MascotasRepositoryImpl : MascotasRepository {

    private val mascotas = mutableListOf(
        Mascota(
            id = "rocky",
            nombre = "Rocky",
            especie = EspecieMascota.PERRO,
            raza = "Labrador",
            edadAnios = 3,
            pesoKg = 28.0,
            sexo = SexoMascota.MACHO,
            castrado = false,
            fotoRes = R.drawable.golden_rocky,
            alergias = listOf("Polen", "Pollo"),
            vacunas = listOf(
                Vacuna("v1", "Antirrábica", "15/07/2023", "15/07/2024", EstadoSalud.ALERTA),
                Vacuna("v2", "Quíntuple", "10/03/2023", "10/03/2024", EstadoSalud.AL_DIA)
            ),
            tratamientos = listOf(
                Tratamiento("t1", "Desparasitación", "01/05/2024", EstadoSalud.AL_DIA)
            ),
            estadoSalud = EstadoSalud.ALERTA,
            alertas = listOf("Vacuna antirrábica en 10 días.")
        ),
        Mascota(
            id = "mishi",
            nombre = "Mishi",
            especie = EspecieMascota.GATO,
            raza = "Siamés",
            edadAnios = 2,
            pesoKg = 4.5,
            sexo = SexoMascota.HEMBRA,
            castrado = true,
            fotoRes = R.drawable.gatito_mushi,
            vacunas = listOf(
                Vacuna("v3", "Triple Felina", "01/01/2024", "01/01/2025", EstadoSalud.AL_DIA)
            ),
            estadoSalud = EstadoSalud.AL_DIA
        ),
        Mascota(
            id = "copito",
            nombre = "Copito",
            especie = EspecieMascota.CONEJO,
            raza = "Enano",
            edadAnios = 1,
            pesoKg = 1.2,
            sexo = SexoMascota.MACHO,
            castrado = false,
            fotoRes = R.drawable.conejo_cookie,
            alergias = listOf("Lechuga iceberg"),
            tratamientos = listOf(
                Tratamiento("t2", "Chequeo general", "15/06/2023", EstadoSalud.VENCIDO)
            ),
            estadoSalud = EstadoSalud.VENCIDO,
            alertas = listOf("Control pendiente.", "Chequeo anual vencido.")
        )
    )

    override suspend fun obtenerMascotas(): List<Mascota> = mascotas.toList()

    override suspend fun obtenerMascota(id: String): Mascota? = mascotas.find { it.id == id }

    override suspend fun agregarMascota(mascota: Mascota): Boolean {
        mascotas.add(mascota)
        return true
    }
}