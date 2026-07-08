package com.example.tpinterfaces.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tpinterfaces.ui.theme.BackgroundApp
import com.example.tpinterfaces.ui.theme.ButtonBackgraundApp
import com.example.tpinterfaces.ui.theme.GreenLight
import com.example.tpinterfaces.ui.theme.TextSecondary
import kotlinx.coroutines.launch

private data class PreguntaFrecuente(
    val pregunta: String,
    val respuesta: String
)

private val preguntasFrecuentes = listOf(
    PreguntaFrecuente(
        pregunta = "¿Cómo solicito un turno para mi mascota?",
        respuesta = "Desde la pestaña Turnos podés tocar Solicitar turno, elegir el servicio, seleccionar día y horario, y confirmar la reserva."
    ),
    PreguntaFrecuente(
        pregunta = "¿Dónde veo los turnos que ya tengo agendados?",
        respuesta = "Entrá en Turnos o en Perfil > Mis turnos. Ahí vas a ver los próximos turnos y la información principal de cada reserva."
    ),
    PreguntaFrecuente(
        pregunta = "¿Cómo agrego una nueva mascota?",
        respuesta = "Desde la pestaña Mascotas tocá el botón para agregar, completá sus datos básicos y guardá el formulario."
    ),
    PreguntaFrecuente(
        pregunta = "¿Puedo revisar vacunas y tratamientos?",
        respuesta = "Sí. En Mascotas, abrí el detalle de una mascota para consultar vacunas, tratamientos, alergias y otros datos de salud registrados."
    ),
    PreguntaFrecuente(
        pregunta = "¿Qué hago si un dato personal está incorrecto?",
        respuesta = "Entrá en Perfil > Datos personales y usá Editar perfil. Si todavía no podés modificarlo desde la app, reportalo desde esta pantalla."
    ),
    PreguntaFrecuente(
        pregunta = "¿La app me avisa cuando vence una vacuna?",
        respuesta = "La sección Perfil puede mostrar recordatorios importantes, como próximos vencimientos o turnos relacionados con tus mascotas."
    )
)

@Composable
fun AyudaSoporteScreen(
    onBack: () -> Unit,
    onEnviarReporte: () -> Unit = {}
) {
    var asunto by rememberSaveable { mutableStateOf("") }
    var detalle by rememberSaveable { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val reporteValido = asunto.isNotBlank() && detalle.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundApp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 96.dp)
        ) {
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
                    text = "Ayuda y soporte",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconContainer {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
                                contentDescription = null,
                                tint = ButtonBackgraundApp,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Preguntas frecuentes",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Respuestas rápidas sobre turnos, mascotas y perfil.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    preguntasFrecuentes.forEachIndexed { index, item ->
                        FaqRow(item)
                        if (index != preguntasFrecuentes.lastIndex) {
                            HorizontalDivider(color = BackgroundApp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconContainer {
                            Icon(
                                imageVector = Icons.Outlined.BugReport,
                                contentDescription = null,
                                tint = ButtonBackgraundApp,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Reportar un problema",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Contanos qué pasó para poder revisarlo.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        }
                    }

                    AppSupportTextField(
                        value = asunto,
                        onValueChange = { asunto = it },
                        label = "Asunto",
                        placeholder = "Ej: No puedo confirmar un turno"
                    )
                    AppSupportTextField(
                        value = detalle,
                        onValueChange = { detalle = it },
                        label = "Detalle",
                        placeholder = "Describí brevemente el problema",
                        minLines = 4
                    )

                    Button(
                        onClick = {
                            onEnviarReporte()
                            asunto = ""
                            detalle = ""
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "El reporte fue enviado, nos pondremos en contacto lo antes posible.",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        },
                        enabled = reporteValido,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonBackgraundApp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Send,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.size(8.dp))
                        Text(
                            text = "Enviar reporte",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

@Composable
private fun FaqRow(item: PreguntaFrecuente) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = item.pregunta,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                    contentDescription = if (expanded) "Ocultar respuesta" else "Mostrar respuesta",
                    tint = ButtonBackgraundApp
                )
            }
        }

        AnimatedVisibility(visible = expanded) {
            Text(
                text = item.respuesta,
                modifier = Modifier.padding(start = 4.dp, end = 44.dp, bottom = 14.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
    }
}

@Composable
private fun AppSupportTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    minLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder, color = Color.LightGray) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        minLines = minLines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ButtonBackgraundApp,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = BackgroundApp,
            unfocusedContainerColor = BackgroundApp
        )
    )
}

@Composable
private fun IconContainer(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(GreenLight),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 820)
@Composable
private fun AyudaSoporteScreenPreview() {
    MaterialTheme {
        AyudaSoporteScreen(onBack = {})
    }
}
