package com.example.tpinterfaces.ui.screens

import androidx.compose.runtime.mutableStateListOf
import java.time.LocalDate
import java.time.LocalTime

enum class EstadoTurno { CONFIRMADO, PENDIENTE, COMPLETADO }

data class Turno(
    val id: Int,
    val titulo: String,
    val mascota: String,
    val fecha: LocalDate,
    val hora: LocalTime,
    val lugar: String,
    val estado: EstadoTurno
)

data class Mascota(val nombre: String, val emoji: String)
data class Servicio(val nombre: String, val descripcion: String)
data class Centro(val nombre: String, val direccion: String, val distancia: String)

object TurnosRepository {

    val mascotas = listOf(
        Mascota("Rocky", "🐕"),
        Mascota("Mishi", "🐈"),
        Mascota("Copito", "🐇")
    )

    val servicios = listOf(
        Servicio("Castración", "Cirugía gratuita"),
        Servicio("Vacunación", "Vacunas municipales"),
        Servicio("Consulta", "Chequeo general")
    )

    val centros = listOf(
        Centro("Centro Municipal de Salud Animal", "Av. Rivadavia 3450", "2.5 km"),
        Centro("Veterinaria Comunitaria Norte", "Av. Cabildo 2100", "4.1 km"),
        Centro("Centro de Bienestar Animal Sur", "Av. Directorio 1800", "5.8 km")
    )

    val horariosBase = listOf(
        LocalTime.of(8, 0),
        LocalTime.of(9, 0),
        LocalTime.of(10, 0),
        LocalTime.of(10, 30),
        LocalTime.of(11, 0),
        LocalTime.of(11, 30),
        LocalTime.of(12, 0),
        LocalTime.of(14, 0),
        LocalTime.of(14, 30),
        LocalTime.of(15, 0),
        LocalTime.of(16, 0),
        LocalTime.of(17, 0)
    )

    val horariosOcupados: Map<Pair<LocalDate, String>, List<LocalTime>> = buildMap {
        val hoy = LocalDate.of(2024, 7, 15)
        put(Pair(hoy, "Centro Municipal de Salud Animal"), listOf(
            LocalTime.of(8, 0), LocalTime.of(9, 0), LocalTime.of(10, 30)
        ))
        put(Pair(hoy.plusDays(1), "Veterinaria Comunitaria Norte"), listOf(
            LocalTime.of(11, 0), LocalTime.of(14, 0), LocalTime.of(15, 0)
        ))
        put(Pair(hoy.plusDays(2), "Centro Municipal de Salud Animal"), listOf(
            LocalTime.of(8, 0), LocalTime.of(9, 0), LocalTime.of(10, 0),
            LocalTime.of(10, 30), LocalTime.of(11, 0), LocalTime.of(11, 30),
            LocalTime.of(12, 0), LocalTime.of(14, 0), LocalTime.of(14, 30),
            LocalTime.of(15, 0), LocalTime.of(16, 0), LocalTime.of(17, 0)
        ))
        put(Pair(hoy.plusDays(4), "Centro de Bienestar Animal Sur"), listOf(
            LocalTime.of(8, 0), LocalTime.of(9, 0), LocalTime.of(10, 0)
        ))
        put(Pair(hoy.plusDays(7), "Veterinaria Comunitaria Norte"), listOf(
            LocalTime.of(14, 0), LocalTime.of(15, 0), LocalTime.of(16, 0), LocalTime.of(17, 0)
        ))
        put(Pair(hoy.plusDays(9), "Centro Municipal de Salud Animal"), listOf(
            LocalTime.of(8, 0), LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(10, 30)
        ))
    }

    fun horariosDisponibles(fecha: LocalDate, centro: String): List<LocalTime> {
        val ocupados = horariosOcupados[Pair(fecha, centro)] ?: emptyList()
        return horariosBase.filter { it !in ocupados }
    }

    private var nextId = 10

    private val _proximos = mutableStateListOf(
        Turno(1, "Vacunación Antirrábica", "Rocky", LocalDate.of(2024, 7, 15), LocalTime.of(10, 30), "Centro Municipal de Salud Animal", EstadoTurno.CONFIRMADO),
        Turno(2, "Chequeo General", "Mishi", LocalDate.of(2024, 7, 22), LocalTime.of(14, 0), "Veterinaria Comunitaria Norte", EstadoTurno.PENDIENTE),
        Turno(3, "Desparasitación", "Rocky", LocalDate.of(2024, 8, 5), LocalTime.of(9, 0), "Centro Municipal de Salud Animal", EstadoTurno.PENDIENTE)
    )

    private val _historial = mutableStateListOf(
        Turno(4, "Castración", "Rocky", LocalDate.of(2024, 3, 10), LocalTime.of(8, 0), "Veterinaria Comunitaria Norte", EstadoTurno.COMPLETADO),
        Turno(5, "Vacunación Triple Felina", "Mishi", LocalDate.of(2024, 1, 20), LocalTime.of(11, 0), "Centro Municipal de Salud Animal", EstadoTurno.COMPLETADO)
    )

    val proximos: List<Turno> get() = _proximos.sortedBy { it.fecha }
    val historial: List<Turno> get() = _historial.sortedByDescending { it.fecha }

    fun agregarTurno(turno: Turno) {
        _proximos.add(turno.copy(id = nextId++))
    }

    fun nuevoTurno(servicio: Servicio, mascota: Mascota, centro: Centro, fecha: LocalDate, hora: LocalTime): Turno {
        return Turno(
            id      = nextId,
            titulo  = servicio.nombre,
            mascota = mascota.nombre,
            fecha   = fecha,
            hora    = hora,
            lugar   = centro.nombre,
            estado  = EstadoTurno.CONFIRMADO
        )
    }
}