package com.example.tpinterfaces.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp




import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private val FlowGreen   = Color(0xFF3A5B3D)
private val FlowGreenLt = Color(0xFFE8F0E9)
private val FlowYellow  = Color(0xFFF8C453)
private val FlowBrown   = Color(0xFF95513C)
private val FlowSurface = Color(0xFFF5F5F2)
private val FlowCard    = Color(0xFFFFFFFF)
private val FlowText    = Color(0xFF1A1A1A)
private val FlowSubtext = Color(0xFF6B7B6E)
private val FlowDisabled = Color(0xFFD0D5D1)

private val localeEs = Locale("es", "AR")
private val dayNameFmt = DateTimeFormatter.ofPattern("EEE", localeEs)
private val confirmDateFmt = DateTimeFormatter.ofPattern("EEE d MMM", localeEs)
private val timeFmt = DateTimeFormatter.ofPattern("HH:mm 'hs'")

@Composable
fun SolicitarTurnoScreen(
    onBack: () -> Unit,
    onConfirmar: () -> Unit
) {
    var paso by remember { mutableIntStateOf(1) }
    var mascotaSeleccionada by remember { mutableStateOf<Mascota?>(null) }
    var servicioSeleccionado by remember { mutableStateOf<Servicio?>(null) }
    var centroSeleccionado by remember { mutableStateOf<Centro?>(null) }
    var fechaSeleccionada by remember { mutableStateOf<LocalDate?>(null) }
    var horaSeleccionada by remember { mutableStateOf<LocalTime?>(null) }

    val hoy = LocalDate.of(2024, 7, 15)

    fun irAtras() {
        if (paso == 1) onBack() else paso--
    }

    Scaffold(
        containerColor = FlowSurface
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TopBar(
                titulo = "Solicitar Turno",
                onBack = ::irAtras,
                mostrarVolver = paso != 6
            )
            StepIndicator(pasoActual = paso, totalPasos = 6)

            AnimatedContent(
                targetState = paso,
                transitionSpec = {
                    if (targetState > initialState)
                        slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
                    else
                        slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
                },
                label = "paso"
            ) { pasoActual ->
                when (pasoActual) {
                    1 -> PasoMascota(
                        seleccionada = mascotaSeleccionada,
                        onSeleccionar = { mascotaSeleccionada = it },
                        onContinuar = { paso = 2 }
                    )
                    2 -> PasoServicio(
                        seleccionado = servicioSeleccionado,
                        onSeleccionar = { servicioSeleccionado = it },
                        onContinuar = { paso = 3 },
                        onVolver = { paso = 1 }
                    )
                    3 -> PasoCentro(
                        seleccionado = centroSeleccionado,
                        onSeleccionar = { centroSeleccionado = it },
                        onContinuar = { paso = 4 },
                        onVolver = { paso = 2 }
                    )
                    4 -> PasoFechaHora(
                        hoy = hoy,
                        centroNombre = centroSeleccionado?.nombre ?: "",
                        fechaSeleccionada = fechaSeleccionada,
                        horaSeleccionada = horaSeleccionada,
                        onFecha = { f -> fechaSeleccionada = f; horaSeleccionada = null },
                        onHora = { horaSeleccionada = it },
                        onContinuar = { paso = 5 },
                        onVolver = { paso = 3 }
                    )
                    5 -> PasoConfirmacion(
                        mascota = mascotaSeleccionada!!,
                        servicio = servicioSeleccionado!!,
                        centro = centroSeleccionado!!,
                        fecha = fechaSeleccionada!!,
                        hora = horaSeleccionada!!,
                        onConfirmar = {
                            val nuevo = TurnosRepository.nuevoTurno(
                                servicioSeleccionado!!,
                                mascotaSeleccionada!!,
                                centroSeleccionado!!,
                                fechaSeleccionada!!,
                                horaSeleccionada!!
                            )

                            TurnosRepository.agregarTurno(nuevo)

                            paso = 6
                        },
                        onVolver = { paso = 4 }
                    )

                    6 -> PasoTurnoConfirmado(
                        mascota = mascotaSeleccionada!!,
                        servicio = servicioSeleccionado!!,
                        centro = centroSeleccionado!!,
                        fecha = fechaSeleccionada!!,
                        hora = horaSeleccionada!!,
                        onFinalizar = onConfirmar
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(titulo: String, onBack: () -> Unit, mostrarVolver: Boolean = true) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (mostrarVolver) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Volver",
                    tint = FlowText
                )
            }
            Spacer(Modifier.width(4.dp))
        }
        Text(
            text = titulo,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = FlowText
        )
    }
}

@Composable
private fun StepIndicator(pasoActual: Int, totalPasos: Int) {
    Row(
        modifier              = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 1..totalPasos) {
            val completado = i < pasoActual
            val activo     = i == pasoActual

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            completado || activo -> FlowGreen
                            else                 -> FlowDisabled
                        }
                    )
            ) {
                if (completado) {
                    Icon(
                        imageVector        = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint               = Color.White,
                        modifier           = Modifier.size(18.dp)
                    )
                } else {
                    Text(
                        text       = "$i",
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color      = if (activo) Color.White else Color.White.copy(alpha = 0.6f)
                    )
                }
            }

            if (i < totalPasos) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .background(if (i < pasoActual) FlowGreen else FlowDisabled)
                )
            }
        }
    }
}

@Composable
private fun PasoMascota(
    seleccionada: Mascota?,
    onSeleccionar: (Mascota) -> Unit,
    onContinuar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        PasoHeader(
            titulo    = "¿Qué mascota?",
            subtitulo = "Seleccioná la mascota para el turno"
        )
        Spacer(Modifier.height(16.dp))
        TurnosRepository.mascotas.forEach { mascota ->
            val activa = seleccionada == mascota
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .clickable { onSeleccionar(mascota) }
                    .then(
                        if (activa) Modifier.border(2.dp, FlowGreen, RoundedCornerShape(16.dp))
                        else Modifier
                    ),
                shape     = RoundedCornerShape(16.dp),
                colors    = CardDefaults.cardColors(
                    containerColor = if (activa) FlowGreenLt else FlowCard
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = if (activa) 0.dp else 2.dp)
            ) {
                Row(
                    modifier          = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier         = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(FlowGreenLt),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = mascota.emoji, fontSize = 28.sp)
                    }
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text       = mascota.nombre,
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = if (activa) FlowGreen else FlowText
                    )
                    if (activa) {
                        Spacer(Modifier.weight(1f))
                        Icon(
                            imageVector        = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint               = FlowGreen,
                            modifier           = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.height(16.dp))
        BotonContinuar(
            habilitado = seleccionada != null,
            onClick    = onContinuar
        )
    }
}

@Composable
private fun PasoServicio(
    seleccionado: Servicio?,
    onSeleccionar: (Servicio) -> Unit,
    onContinuar: () -> Unit,
    onVolver: () -> Unit
) {
    val iconos = mapOf(
        "Castración"  to Icons.Outlined.MedicalServices,
        "Vacunación"  to Icons.Outlined.MedicalServices,
        "Consulta"    to Icons.Outlined.Pets
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        PasoHeader(
            titulo    = "¿Qué servicio?",
            subtitulo = "Elegí el tipo de atención"
        )
        Spacer(Modifier.height(12.dp))

        ResumenSeleccion(
            mascota = Mascota(
                nombre = "Rocky",
                emoji = "🐶"
            ),
            servicio = null,
            centro = null,
            fecha = null,
            hora = null
        )

        Spacer(Modifier.height(16.dp))
        Spacer(Modifier.height(16.dp))
        TurnosRepository.servicios.forEach { servicio ->
            val activo = seleccionado == servicio
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .clickable { onSeleccionar(servicio) }
                    .then(
                        if (activo) Modifier.border(2.dp, FlowGreen, RoundedCornerShape(16.dp))
                        else Modifier
                    ),
                shape     = RoundedCornerShape(16.dp),
                colors    = CardDefaults.cardColors(
                    containerColor = if (activo) FlowGreenLt else FlowCard
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = if (activo) 0.dp else 2.dp)
            ) {
                Row(
                    modifier          = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier         = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(FlowGreenLt),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector        = iconos[servicio.nombre] ?: Icons.Outlined.MedicalServices,
                            contentDescription = null,
                            tint               = FlowGreen,
                            modifier           = Modifier.size(24.dp)
                        )
                    }
                    Spacer(Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text       = servicio.nombre,
                            fontSize   = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = if (activo) FlowGreen else FlowText
                        )
                        Text(
                            text     = servicio.descripcion,
                            fontSize = 13.sp,
                            color    = FlowSubtext
                        )
                    }
                    if (activo) {
                        Icon(
                            imageVector        = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint               = FlowGreen,
                            modifier           = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.height(16.dp))
        BotonContinuar(habilitado = seleccionado != null, onClick = onContinuar)
        Spacer(Modifier.height(8.dp))
        BotonVolver(onVolver)
    }
}

@Composable
private fun PasoCentro(
    seleccionado: Centro?,
    onSeleccionar: (Centro) -> Unit,
    onContinuar: () -> Unit,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        PasoHeader(
            titulo    = "¿Qué centro?",
            subtitulo = "Seleccioná el centro más cercano"
        )
        Spacer(Modifier.height(12.dp))

        ResumenSeleccion(
            mascota = Mascota(
                nombre = "Rocky",
                emoji = "🐶"
            ),
            servicio = Servicio(
                nombre = "Castración",
                descripcion = "Cirugía programada"
            ),
            centro = null,
            fecha = null,
            hora = null
        )

        Spacer(Modifier.height(16.dp))
        Spacer(Modifier.height(16.dp))
        TurnosRepository.centros.forEach { centro ->
            val activo = seleccionado == centro
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .clickable { onSeleccionar(centro) }
                    .then(
                        if (activo) Modifier.border(2.dp, FlowGreen, RoundedCornerShape(16.dp))
                        else Modifier
                    ),
                shape     = RoundedCornerShape(16.dp),
                colors    = CardDefaults.cardColors(
                    containerColor = if (activo) FlowGreenLt else FlowCard
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = if (activo) 0.dp else 2.dp)
            ) {
                Row(
                    modifier          = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier         = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(FlowGreenLt),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector        = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint               = FlowGreen,
                            modifier           = Modifier.size(24.dp)
                        )
                    }
                    Spacer(Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text       = centro.nombre,
                            fontSize   = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = if (activo) FlowGreen else FlowText
                        )
                        Text(text = centro.direccion, fontSize = 12.sp, color = FlowSubtext)
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text       = centro.distancia,
                            fontSize   = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = FlowGreen
                        )
                    }
                    if (activo) {
                        Icon(
                            imageVector        = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint               = FlowGreen,
                            modifier           = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.height(16.dp))
        BotonContinuar(habilitado = seleccionado != null, onClick = onContinuar)
        Spacer(Modifier.height(8.dp))
        BotonVolver(onVolver)
    }
}

@Composable
private fun PasoFechaHora(
    hoy: LocalDate,
    centroNombre: String,
    fechaSeleccionada: LocalDate?,
    horaSeleccionada: LocalTime?,
    onFecha: (LocalDate) -> Unit,
    onHora: (LocalTime) -> Unit,
    onContinuar: () -> Unit,
    onVolver: () -> Unit
) {
    val diasDisponibles = (0 until 14)
        .map { hoy.plusDays(it.toLong()) }
        .filter { it.dayOfWeek.value != 7 }

    val horarios = fechaSeleccionada?.let {
        TurnosRepository.horariosDisponibles(it, centroNombre)
    } ?: emptyList()

    val horariosOcupados = fechaSeleccionada?.let {
        TurnosRepository.horariosOcupados[Pair(it, centroNombre)] ?: emptyList()
    } ?: emptyList()

    val todoOcupado = fechaSeleccionada != null && horarios.isEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        PasoHeader(titulo = "¿Cuándo?", subtitulo = "Elegí fecha y horario")
        Spacer(Modifier.height(12.dp))

        ResumenSeleccion(
            mascota = Mascota(
                nombre = "Rocky",
                emoji = "🐶"
            ),
            servicio = Servicio(
                nombre = "Castración",
                descripcion = "Cirugía programada"
            ),
            centro = Centro(
                nombre = "Centro Municipal de Salud Animal",
                direccion = "Av. Siempre Viva 123",
                distancia = "1,2 km"
            ),
            fecha = null,
            hora = null
        )

        Spacer(Modifier.height(16.dp))
        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector        = Icons.Outlined.CalendarMonth,
                contentDescription = null,
                tint               = FlowGreen,
                modifier           = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text       = "Julio – Agosto 2024",
                fontSize   = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color      = FlowText
            )
        }

        Spacer(Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(diasDisponibles) { dia ->
                val activo     = fechaSeleccionada == dia
                val esDomingo  = dia.dayOfWeek.value == 7

                Column(
                    modifier = Modifier
                        .width(58.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            when {
                                activo    -> FlowGreen
                                esDomingo -> FlowDisabled.copy(alpha = 0.4f)
                                else      -> FlowCard
                            }
                        )
                        .border(
                            width  = if (activo) 0.dp else 1.dp,
                            color  = if (activo) Color.Transparent else FlowDisabled,
                            shape  = RoundedCornerShape(14.dp)
                        )
                        .clickable(enabled = !esDomingo) { onFecha(dia) }
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text     = dia.format(dayNameFmt).uppercase(),
                        fontSize = 10.sp,
                        color    = when {
                            activo    -> Color.White.copy(alpha = 0.8f)
                            esDomingo -> FlowDisabled
                            else      -> FlowSubtext
                        }
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text       = "${dia.dayOfMonth}",
                        fontSize   = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color      = when {
                            activo    -> Color.White
                            esDomingo -> FlowDisabled
                            else      -> FlowText
                        }
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text     = dia.month.getDisplayName(TextStyle.SHORT, localeEs)
                            .replaceFirstChar { it.uppercase() },
                        fontSize = 10.sp,
                        color    = when {
                            activo    -> Color.White.copy(alpha = 0.8f)
                            esDomingo -> FlowDisabled
                            else      -> FlowSubtext
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        if (fechaSeleccionada != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector        = Icons.Outlined.Schedule,
                    contentDescription = null,
                    tint               = FlowGreen,
                    modifier           = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text       = "Horarios disponibles",
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = FlowText
                )
            }

            Spacer(Modifier.height(12.dp))

            if (todoOcupado) {
                Card(
                    modifier  = Modifier.fillMaxWidth(),
                    shape     = RoundedCornerShape(14.dp),
                    colors    = CardDefaults.cardColors(containerColor = Color(0xFFFFF3CD))
                ) {
                    Text(
                        text      = "No hay turnos disponibles para este día en este centro. Probá otro día u otro centro.",
                        fontSize  = 13.sp,
                        color     = Color(0xFF856404),
                        modifier  = Modifier.padding(14.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                val todosHorarios = TurnosRepository.horariosBase
                LazyVerticalGrid(
                    columns             = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement   = Arrangement.spacedBy(8.dp),
                    modifier            = Modifier.heightIn(max = 280.dp)
                ) {
                    items(todosHorarios) { hora ->
                        val disponible = hora in horarios
                        val activa     = horaSeleccionada == hora

                        Box(
                            modifier         = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    when {
                                        activa      -> FlowGreen
                                        !disponible -> FlowDisabled.copy(alpha = 0.3f)
                                        else        -> FlowCard
                                    }
                                )
                                .border(
                                    width = 1.dp,
                                    color = when {
                                        activa      -> Color.Transparent
                                        !disponible -> FlowDisabled
                                        else        -> FlowDisabled
                                    },
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable(enabled = disponible) { onHora(hora) }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text       = hora.format(timeFmt),
                                fontSize   = 13.sp,
                                fontWeight = if (activa) FontWeight.Bold else FontWeight.Normal,
                                color      = when {
                                    activa      -> Color.White
                                    !disponible -> FlowDisabled
                                    else        -> FlowText
                                },
                                textAlign  = TextAlign.Center
                            )
                        }
                    }
                }

                if (horariosOcupados.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text     = "Los horarios en gris ya están ocupados",
                        fontSize = 11.sp,
                        color    = FlowSubtext,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Box(
                modifier         = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text      = "Seleccioná un día para ver los horarios",
                    fontSize  = 13.sp,
                    color     = FlowSubtext,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(Modifier.weight(1f))
        Spacer(Modifier.height(16.dp))
        BotonContinuar(
            habilitado = fechaSeleccionada != null && horaSeleccionada != null,
            onClick    = onContinuar
        )
        Spacer(Modifier.height(8.dp))
        BotonVolver(onVolver)
    }
}

@Composable
private fun PasoConfirmacion(
    mascota: Mascota,
    servicio: Servicio,
    centro: Centro,
    fecha: LocalDate,
    hora: LocalTime,
    onConfirmar: () -> Unit,
    onVolver: () -> Unit
) {
    Column(
        modifier              = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment   = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(12.dp))

        Box(
            modifier         = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(FlowGreenLt),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.Outlined.CheckCircle,
                contentDescription = null,
                tint               = FlowGreen,
                modifier           = Modifier.size(40.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text       = "Confirmá tu turno",
            fontSize   = 22.sp,
            fontWeight = FontWeight.Bold,
            color      = FlowText,
            textAlign  = TextAlign.Center
        )
        Text(
            text      = "Revisá los detalles antes de confirmar",
            fontSize  = 14.sp,
            color     = FlowSubtext,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        Card(
            modifier  = Modifier.fillMaxWidth(),
            shape     = RoundedCornerShape(16.dp),
            colors    = CardDefaults.cardColors(containerColor = FlowCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                ConfirmRow(
                    icono  = Icons.Outlined.CalendarMonth,
                    label  = "Fecha y hora",
                    valor  = "${fecha.format(confirmDateFmt)} · ${hora.format(timeFmt)}"
                )
                HorizontalDivider(color = FlowSurface, thickness = 1.dp)
                ConfirmRow(
                    icono  = Icons.Outlined.LocationOn,
                    label  = "Centro",
                    valor  = centro.nombre
                )
                HorizontalDivider(color = FlowSurface, thickness = 1.dp)
                ConfirmRow(
                    icono  = Icons.Outlined.MedicalServices,
                    label  = "Servicio",
                    valor  = servicio.nombre
                )
                HorizontalDivider(color = FlowSurface, thickness = 1.dp)
                ConfirmRow(
                    icono  = Icons.Outlined.Pets,
                    label  = "Mascota",
                    valor  = mascota.nombre
                )
            }
        }

        Spacer(Modifier.weight(1f))
        Spacer(Modifier.height(24.dp))

        Button(
            onClick  = onConfirmar,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape    = RoundedCornerShape(14.dp),
            colors   = ButtonDefaults.buttonColors(containerColor = FlowGreen)
        ) {
            Text(
                text       = "Confirmar Turno",
                fontSize   = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color      = Color.White
            )
        }
        Spacer(Modifier.height(8.dp))
        BotonVolver(onVolver)
    }
}

@Composable
private fun ConfirmRow(icono: ImageVector, label: String, valor: String) {
    Row(
        modifier          = Modifier.padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier         = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(FlowGreenLt),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = icono,
                contentDescription = null,
                tint               = FlowGreen,
                modifier           = Modifier.size(20.dp)
            )
        }
        Spacer(Modifier.width(14.dp))
        Column {
            Text(text = label, fontSize = 11.sp, color = FlowSubtext)
            Text(
                text       = valor,
                fontSize   = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color      = FlowText
            )
        }
    }
}

@Composable
private fun PasoHeader(titulo: String, subtitulo: String) {
    Column {
        Text(
            text       = titulo,
            fontSize   = 20.sp,
            fontWeight = FontWeight.Bold,
            color      = FlowText
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text     = subtitulo,
            fontSize = 13.sp,
            color    = FlowSubtext
        )
    }
}

@Composable
private fun BotonContinuar(habilitado: Boolean, onClick: () -> Unit) {
    Button(
        onClick  = onClick,
        enabled  = habilitado,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape    = RoundedCornerShape(14.dp),
        colors   = ButtonDefaults.buttonColors(
            containerColor         = FlowGreen,
            disabledContainerColor = FlowGreen.copy(alpha = 0.35f)
        )
    ) {
        Text(
            text       = "Continuar",
            fontSize   = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color      = Color.White
        )
        Spacer(Modifier.width(6.dp))
        Icon(
            imageVector        = Icons.AutoMirrored.Outlined.ArrowForward,
            contentDescription = null,
            tint               = Color.White,
            modifier           = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun BotonVolver(onClick: () -> Unit) {
    TextButton(
        onClick  = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector        = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = null,
            tint               = FlowSubtext,
            modifier           = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text     = "Volver",
            fontSize = 14.sp,
            color    = FlowSubtext
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 820)
@Composable
private fun SolicitarTurnoPreview() {
    MaterialTheme {
        SolicitarTurnoScreen(onBack = {}, onConfirmar = {})
    }
}

@Composable
private fun ResumenSeleccion(
    mascota: Mascota?,
    servicio: Servicio?,
    centro: Centro?,
    fecha: LocalDate?,
    hora: LocalTime?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = FlowGreenLt)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                text = "Resumen",
                fontWeight = FontWeight.Bold,
                color = FlowGreen
            )

            Spacer(Modifier.height(8.dp))

            mascota?.let {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Pets,
                        contentDescription = null,
                        tint = FlowGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("${it.nombre}")
                }
            }

            servicio?.let {
                Spacer(Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.MedicalServices,
                        contentDescription = null,
                        tint = FlowGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("${it.nombre}")
                }
            }

            centro?.let {
                Spacer(Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = FlowGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("${it.nombre}")
                }
            }

            fecha?.let {
                Spacer(Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint = FlowGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("${it.format(confirmDateFmt)}")
                }
            }

            hora?.let {
                Spacer(Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = FlowGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("${it.format(timeFmt)}")
                }
            }
        }
    }
}

@Composable
private fun PasoTurnoConfirmado(
    mascota: Mascota,
    servicio: Servicio,
    centro: Centro,
    fecha: LocalDate,
    hora: LocalTime,
    onFinalizar: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(FlowGreenLt),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = null,
                tint = FlowGreen,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "¡Turno confirmado!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = FlowText
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Tu turno fue registrado correctamente.",
            fontSize = 14.sp,
            color = FlowSubtext,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = FlowCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(4.dp)) {

                ConfirmRow(
                    icono = Icons.Outlined.CalendarMonth,
                    label = "Fecha y hora",
                    valor = "${fecha.format(confirmDateFmt)} · ${hora.format(timeFmt)}"
                )

                HorizontalDivider(
                    color = FlowSurface,
                    thickness = 1.dp
                )

                ConfirmRow(
                    icono = Icons.Outlined.LocationOn,
                    label = "Centro",
                    valor = centro.nombre
                )

                HorizontalDivider(
                    color = FlowSurface,
                    thickness = 1.dp
                )

                ConfirmRow(
                    icono = Icons.Outlined.MedicalServices,
                    label = "Servicio",
                    valor = servicio.nombre
                )

                HorizontalDivider(
                    color = FlowSurface,
                    thickness = 1.dp
                )

                ConfirmRow(
                    icono = Icons.Outlined.Pets,
                    label = "Mascota",
                    valor = mascota.nombre
                )
            }
        }
        
        Spacer(Modifier.height(32.dp))

        Button(
            onClick = onFinalizar,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = FlowGreen
            )
        ) {
            Text(
                "Ver mis turnos",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(12.dp))
    }
}