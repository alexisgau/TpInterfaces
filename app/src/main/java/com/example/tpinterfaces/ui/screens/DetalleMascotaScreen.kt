package com.example.tpinterfaces.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tpinterfaces.R
import com.example.tpinterfaces.data.model.EstadoSalud
import com.example.tpinterfaces.data.repository.MascotasRepositoryImpl
import com.example.tpinterfaces.ui.theme.BackgroundApp
import com.example.tpinterfaces.ui.theme.Green
import com.example.tpinterfaces.ui.theme.Red
import com.example.tpinterfaces.ui.theme.TextSecondary
import com.example.tpinterfaces.ui.theme.Yellow
import com.example.tpinterfaces.ui.theme.YellowSoft
import com.example.tpinterfaces.ui.viewModel.DetalleMascotaViewModel
import com.example.tpinterfaces.ui.viewModel.DetalleMascotaViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleMascotaScreen(
    mascotaId: String,
    onBack: () -> Unit,
    viewModel: DetalleMascotaViewModel = viewModel(
        factory = DetalleMascotaViewModelFactory(
            mascotaId = mascotaId,
            repository = MascotasRepositoryImpl()
        )
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.cargando) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val mascota = uiState.mascota ?: return

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF6F7F4))) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // ── Hero imagen full width ──────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)) {
                Image(
                    painter = painterResource(id = mascota.fotoRes ?: R.drawable.ic_avatar_defecto),
                    contentDescription = mascota.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Degradado inferior para que el contenido no corte bruscamente
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, BackgroundApp)
                            )
                        )
                )
            }
            // ── Contenido con overlap sobre la imagen ───────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-24).dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(BackgroundApp)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(20.dp))

                // Card principal nombre + datos
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                mascota.nombre,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color(0xFFE8F5EC)
                            ) {
                                Text(
                                    mascota.especie.label,
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 4.dp
                                    ),
                                    color = Color(0xFF2E7D52),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "${mascota.raza} · ${mascota.edadAnios} años · ${mascota.pesoKg} kg",
                            color = Color.Gray,
                            style = MaterialTheme.typography.titleMedium
                        )
                        if (mascota.castrado) {
                            Spacer(Modifier.height(8.dp))
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color(0xFFEDE7F6)
                            ) {
                                Text(
                                    "Castrado/a",
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 3.dp
                                    ),
                                    color = Color(0xFF7E57C2),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Vacunas
                SeccionSalud(
                    titulo = "Vacunas",
                    iconoRes = R.drawable.ic_vaccines
                ) {
                    if (mascota.vacunas.isEmpty()) {
                        TextoVacio("Sin vacunas registradas")
                    } else {
                        mascota.vacunas.forEach { vacuna ->
                            FilaSalud(
                                nombre = vacuna.nombre,
                                linea1 = "Última: ${vacuna.ultimaFecha}",
                                linea2 = "Próx: ${vacuna.proximaFecha}",
                                estado = vacuna.estado
                            )
                            if (vacuna != mascota.vacunas.last()) {
                                HorizontalDivider(
                                    color = TextSecondary,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Tratamientos
                SeccionSalud(
                    titulo = "Tratamientos",
                    iconoRes = R.drawable.ic_medication
                ) {
                    if (mascota.tratamientos.isEmpty()) {
                        TextoVacio("Sin tratamientos registrados")
                    } else {
                        mascota.tratamientos.forEach { tratamiento ->
                            FilaSalud(
                                nombre = tratamiento.nombre,
                                linea1 = tratamiento.fecha,
                                estado = tratamiento.estado
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Alergias
                SeccionSalud(
                    titulo = "Alergias",
                    iconoRes = R.drawable.ic_spa
                ) {
                    if (mascota.alergias.isEmpty()) {
                        TextoVacio("Sin alergias registradas")
                    } else {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            mascota.alergias.forEach { alergia ->
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = Color(0xFFE91E63)
                                ) {
                                    Text(
                                        alergia,
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        ),
                                        color = YellowSoft,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(32.dp))
            }
        }
        // ── Botones flotantes sobre la imagen ──────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 48.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BotonFlotante(
                icono = Icons.Default.ArrowBackIosNew,
                onClick = onBack
            )
            BotonFlotante(
                icono = Icons.Default.Edit,
                onClick = { /* editar */ }
            )
        }
    }
}

@Composable
private fun BotonFlotante(icono: ImageVector, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(BackgroundApp.copy(alpha = 0.85f))
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = TextSecondary
        )
    }
}

@Composable
private fun SeccionSalud(titulo: String, iconoRes: Int, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = iconoRes),
                    contentDescription = null,
                    tint = Color(0xFF2E7D52),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    titulo,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF1B5E20)
                )
            }
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun FilaSalud(nombre: String, linea1: String, linea2: String? = null, estado: EstadoSalud) {
    val estadoColor = when (estado) {
        EstadoSalud.AL_DIA  -> Green
        EstadoSalud.ALERTA  -> Yellow
        EstadoSalud.VENCIDO -> Red
    }
    val estadoTexto = when (estado) {
        EstadoSalud.AL_DIA  -> "Al día"
        EstadoSalud.ALERTA  -> "Próxima"
        EstadoSalud.VENCIDO -> "Vencido"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = nombre,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = linea1,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            if (linea2 != null) {
                Text(
                    text = linea2,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }
        }
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = estadoColor.copy(alpha = 0.12f)
        ) {
            Text(
                estadoTexto,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                color = estadoColor,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun TextoVacio(texto: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(texto, color = Color.LightGray, style = MaterialTheme.typography.bodyMedium)
    }
}
/*
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.mascota?.nombre ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundApp)
            )
        },
        containerColor = BackgroundApp
    ) { padding ->
        if (uiState.cargando) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        val mascota = uiState.mascota ?: return@Scaffold

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            // Hero card con datos principales
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(
                            id = mascota.fotoRes ?: R.drawable.ic_avatar_defecto
                        ),
                        contentDescription = mascota.nombre,
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8F5EC)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "${mascota.nombre} · ${mascota.especie.label}",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "${mascota.raza} · ${mascota.edadAnios} años · ${mascota.pesoKg} kg",
                            color = Color.Gray
                        )
                        Spacer(Modifier.height(6.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            ChipInfo(mascota.sexo.label)
                            if (mascota.castrado) ChipInfo("Castrado/a")
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Vacunas
            SeccionDetalle(
                titulo = "Vacunas",
                iconoRes = R.drawable.ic_vaccines
            ) {
                if (mascota.vacunas.isEmpty()) {
                    TextoVacio("No hay vacunas registradas")
                } else {
                    mascota.vacunas.forEach { vacuna ->
                        FilaSaludItem(
                            nombre = vacuna.nombre,
                            linea1 = "Última: ${vacuna.ultimaFecha}",
                            linea2 = "Próx: ${vacuna.proximaFecha}",
                            estado = vacuna.estado
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Tratamientos
            SeccionDetalle(
                titulo = "Tratamientos",
                iconoRes = R.drawable.ic_medication
            ) {
                if (mascota.tratamientos.isEmpty()) {
                    TextoVacio("Sin tratamientos registrados")
                } else {
                    mascota.tratamientos.forEach { tratamiento ->
                        FilaSaludItem(
                            nombre = tratamiento.nombre,
                            linea1 = tratamiento.fecha,
                            estado = tratamiento.estado
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Alergias
            if (mascota.alergias.isNotEmpty()) {
                SeccionDetalle(
                    titulo = "Alergias",
                    iconoRes = R.drawable.ic_spa) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        mascota.alergias.forEach { alergia ->
                            AssistChip(
                                onClick = {},
                                label = { Text(alergia) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = Color(0xFFFCE4EC)
                                )
                            )
                        }
                    }
                }
                Spacer(Modifier.height(24.dp))
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SeccionDetalle(
    titulo: String, 
    iconoRes: Int, 
    content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = iconoRes), contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(titulo, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun FilaSaludItem(
    nombre: String,
    linea1: String,
    linea2: String? = null,
    estado: EstadoSalud
) {
    val (color, texto, icono) =
        when (estado) {
        EstadoSalud.AL_DIA -> Triple(Green, "Al día", Icons.Default.Check)
        EstadoSalud.ALERTA -> Triple(Yellow, "Alerta", Icons.Default.Warning)
        EstadoSalud.VENCIDO -> Triple(Red, "Vencido", Icons.Default.Error)
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(nombre, fontWeight = FontWeight.Medium)
            Text(linea1, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            if (linea2 != null) Text(linea2, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(color.copy(alpha = 0.12f))
                .padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(14.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                texto,
                color = color,
                style = MaterialTheme.typography.labelSmall)
        }
    }
    HorizontalDivider(
        color = Color(0xFFF5F5F5),
        modifier =
            Modifier.padding(top = 4.dp)
    )
}

@Composable
private fun ChipInfo(texto: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFE8F5EC))
            .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF2E7D52)
        )
    }
}

@Composable
private fun TextoVacio(texto: String) {
    Text(
        text = texto,
        color = Color.Gray,
        style = MaterialTheme.typography.bodyMedium
    )
}*/