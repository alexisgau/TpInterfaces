package com.example.tpinterfaces.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Coronavirus
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.NotInterested
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tpinterfaces.data.model.EspecieMascota
import com.example.tpinterfaces.data.model.SexoMascota
import com.example.tpinterfaces.data.repository.MascotasRepositoryImpl
import com.example.tpinterfaces.ui.theme.BackgroundApp
import com.example.tpinterfaces.ui.theme.ButtonBackgraundApp
import com.example.tpinterfaces.ui.viewModel.AgregarMascotaViewModel
import com.example.tpinterfaces.ui.viewModel.AgregarMascotaViewModelFactory

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AgregarMascotaScreen(
    onBack: () -> Unit,
    onGuardado: () -> Unit,
    viewModel: AgregarMascotaViewModel = viewModel(
        factory = AgregarMascotaViewModelFactory(MascotasRepositoryImpl())
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.guardadoExitoso) {
        if (uiState.guardadoExitoso) onGuardado()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundApp)
    ) {

        // Contenido scrolleable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 84.dp) // espacio para el botón de abajo
                .padding(horizontal = 16.dp)
        ) {
            // TopBar manual
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Volver"
                    )
                }
                Text(
                    "Nueva mascota",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(12.dp))

            // Avatar placeholder
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8F5EC))
                    .border(
                        width = 2.dp,
                        Color(0xFF2E7D52).copy(alpha = 0.3f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Foto",
                        tint = Color(0xFF2E7D52),
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        "Foto",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color(0xFF2E7D52)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            SeccionFormulario(titulo = "Nombre") {
                AppTextField(
                    value = uiState.nombre,
                    onValueChange = viewModel::onNombreChange,
                    placeholder = "Ej: Rocky",
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    )
                )
            }

            Spacer(Modifier.height(14.dp))

            SeccionFormulario(titulo = "Especie") {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    EspecieMascota.entries.forEach { especie ->
                        val seleccionada = uiState.especie == especie
                        FilterChip(
                            selected = seleccionada,
                            onClick = { viewModel.onEspecieChange(especie) },
                            label = { Text(especie.label) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ButtonBackgraundApp,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            SeccionFormulario(titulo = "Raza") {
                AppTextField(
                    value = uiState.raza,
                    onValueChange = viewModel::onRazaChange,
                    placeholder = "Ej: Labrador",
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    )
                )
            }

            Spacer(Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    SeccionFormulario(titulo = "Edad (años)") {
                        AppTextField(
                            value = uiState.edadAnios,
                            onValueChange = viewModel::onEdadChange,
                            placeholder = "3",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            leadingIconRes = Icons.Default.Cake
                        )
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    SeccionFormulario(titulo = "Peso (kg)") {
                        AppTextField(
                            value = uiState.pesoKg,
                            onValueChange = viewModel::onPesoChange,
                            placeholder = "28.0",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            leadingIconRes = Icons.Default.MonitorWeight
                        )
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            SeccionFormulario(titulo = "Sexo") {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SexoMascota.entries.forEach { sexo ->
                        val seleccionado = uiState.sexo == sexo
                        FilterChip(
                            selected = seleccionado,
                            onClick = { viewModel.onSexoChange(sexo) },
                            label = { Text(sexo.label) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ButtonBackgraundApp,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Castrado/a", fontWeight = FontWeight.Medium)
                    Switch(
                        checked = uiState.castrado,
                        onCheckedChange = viewModel::onCastradoChange,
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = ButtonBackgraundApp
                        )
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            SeccionFormulario(titulo = "Alergias conocidas (opcional)") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AppTextField(
                        value = uiState.alergiaInput,
                        onValueChange = viewModel::onAlergiaInputChange,
                        placeholder = "Ej: Polen",
                        modifier = Modifier.weight(1f),
                        leadingIconRes = Icons.Default.Coronavirus
                    )
                    IconButton(
                        onClick = viewModel::agregarAlergia,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(ButtonBackgraundApp)
                            .size(52.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar",
                            tint = Color.White
                        )
                    }
                }
                if (uiState.alergias.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        uiState.alergias.forEach { alergia ->
                            InputChip(
                                selected = false,
                                onClick = { viewModel.quitarAlergia(alergia) },
                                label = { Text(alergia) },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Quitar",
                                        modifier = Modifier.size(16.dp)
                                    )
                                },
                                colors = InputChipDefaults.inputChipColors(
                                    containerColor = Color(
                                        0xFFFCE4EC
                                    )
                                )
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }

        // Botón fijo abajo
        Button(
            onClick = viewModel::guardar,
            enabled = uiState.formularioValido && !uiState.guardando,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            if (uiState.guardando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Guardar mascota",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
@Composable
private fun SeccionFormulario(
    titulo: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            titulo,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )
        content()
    }
}

@Composable
private fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIconRes: ImageVector = Icons.Default.NotInterested
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.LightGray) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ButtonBackgraundApp,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        leadingIcon = leadingIconRes.let {
            { Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp),
                tint = Color.Gray)
            }
        }
    )
}