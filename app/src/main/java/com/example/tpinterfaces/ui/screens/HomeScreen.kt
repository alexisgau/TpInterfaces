package com.example.tpinterfaces.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tpinterfaces.R
import com.example.tpinterfaces.branding.BrandConfig
import com.example.tpinterfaces.branding.LocalBrand
import com.example.tpinterfaces.data.model.Campania
import com.example.tpinterfaces.data.model.Noticia
import com.example.tpinterfaces.data.model.QuickAction
import com.example.tpinterfaces.data.model.Recordatorio
import com.example.tpinterfaces.data.repository.HomeRepository
import com.example.tpinterfaces.data.repository.HomeRepositoryImpl
import com.example.tpinterfaces.ui.theme.GreenPrimary
import com.example.tpinterfaces.ui.theme.YellowBackLocation
import com.example.tpinterfaces.ui.theme.YellowIconLocation
import com.example.tpinterfaces.ui.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            repository = HomeRepositoryImpl()
        )
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    val brand = LocalBrand.current

    if (uiState.cargando) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(12.dp))

        // 1. Card del municipio (White Label) — la del cartel rojo
        MunicipioHeaderCard(brand = brand)

        Spacer(Modifier.height(16.dp))

        // 2. Saludo + ayuda
        uiState.usuario?.let {
            GreetingHeader(
                nombreUsuario = it.nombre,
                fotoUrl = it.fotoUrl,
                hayNotificaciones = uiState.hayNotificaciones,
                onNotificationClick = viewModel::onNotificationClick
            )
        }

        Spacer(Modifier.height(16.dp))

        // 3. Campaña activa
        uiState.campania?.let {
            CampaignBannerCard(campania = it)
            Spacer(Modifier.height(20.dp))
        }

        // 4. Recordatorios
        uiState.recordatorios.forEach { recordatorio ->
            ReminderCard(
                recordatorio = recordatorio,
                onClick = {},
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 3.dp,
                        color = YellowIconLocation,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        color = YellowBackLocation,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
                )
            Spacer(Modifier.height(16.dp))
        }

        // 5. Acciones rápidas
        QuickActionsGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            acciones = uiState.acciones,
            onActionClick = {
                viewModel.onAccionRapidaClick(it.id)
            }
        )

        Spacer(Modifier.height(16.dp))

        // 6. Noticias de la comunidad
        if (uiState.noticias.isNotEmpty()) {
            Text(
                text = "Noticias de la comunidad",
                style = MaterialTheme.typography.titleLarge,
                color = GreenPrimary
            )
            uiState.noticias.forEach { noticia ->
                NewsItemCard(noticia = noticia, onClick = {})
            }
        }
    }
}

@Composable
fun MunicipioHeaderCard(brand: BrandConfig) {
    Column {
        Image(
            painter = painterResource(brand.bannerRes),
            contentDescription = brand.nombreMunicipio,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}

class HomeViewModelFactory(
    private val repository: HomeRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}

@Composable
fun GreetingHeader(
    nombreUsuario: String,
    fotoUrl: String?,
    hayNotificaciones: Boolean,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = nombreUsuario,
                    tint = Color.DarkGray
                )
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        "Hola",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        nombreUsuario + "\uD83D\uDC4B",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF1F1F1))
                    .clickable { onNotificationClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.NotificationsNone,
                    contentDescription = nombreUsuario,
                    tint = Color.DarkGray
                )
                if (hayNotificaciones) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                    )
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Text(
            "¿Cómo podemos ayudarte hoy?",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun CampaignBannerCard(
    campania: Campania,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Image(
                painter = painterResource(R.drawable.fondo_campania_castracion),
                contentDescription = "Imagen de fondo",
                contentScale = ContentScale.Crop, // Ajusta cómo se estira la imagen
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = "Campañas",
                    tint = Color.DarkGray
                )
                Spacer(Modifier.width(6.dp))
                Text("Campaña Activa", color = Color.White)
            }
            Text(
                campania.titulo,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                campania.subtitulo,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
fun ReminderCard(
    recordatorio: Recordatorio,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(YellowBackLocation),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = recordatorio.imageVector,
                contentDescription = "Recordatorio",
                tint = Color.DarkGray
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ){
            Text(
                text = recordatorio.titulo,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                recordatorio.descripcion,
                style = MaterialTheme.typography.titleMedium
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Recordatorio",
                    tint = Color.DarkGray,
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    "Vence el ${recordatorio.fechaLimite}",
                    color = YellowIconLocation,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Siguiente",
            tint = Color.DarkGray
        )
    }
}

@Composable
fun QuickActionsGrid(
    modifier: Modifier,
    acciones: List<QuickAction>,
    onActionClick: (QuickAction) -> Unit
) {
    Spacer(Modifier.height(16.dp))
    Text(
        text = "Acciones Rápidas",
        style = MaterialTheme.typography.titleLarge,
        color = GreenPrimary
    )
    Spacer(Modifier.height(16.dp))
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        acciones.chunked(2).forEach { fila ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                fila.forEach { accion ->
                    Box(modifier = Modifier.weight(1f)) {
                        QuickActionItem(
                            accion = accion,
                            onClick = { onActionClick(accion) }
                        )
                    }
                }

                // Si la última fila tiene un solo elemento
                if (fila.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun QuickActionItem(
    accion: QuickAction,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(accion.colorFondo),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(accion.iconoRes),
                contentDescription = accion.titulo,
                tint = accion.colorIcono
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = accion.titulo,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun NewsItemCard(
    noticia: Noticia,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Imagen (placeholder por ahora si no usás Coil)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Newspaper,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = noticia.titulo,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = noticia.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }
        }
    }
}