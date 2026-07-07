package com.example.tpinterfaces.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Vaccines
import com.example.tpinterfaces.R
import com.example.tpinterfaces.branding.BrandCatalog
import com.example.tpinterfaces.branding.BrandConfig
import com.example.tpinterfaces.ui.theme.*
import com.example.tpinterfaces.data.model.Campania
import com.example.tpinterfaces.data.model.Noticia
import com.example.tpinterfaces.data.model.QuickAction
import com.example.tpinterfaces.data.model.Recordatorio
import com.example.tpinterfaces.data.model.Usuario
import com.example.tpinterfaces.ui.viewModel.HomeUiState

class HomeRepositoryImpl : HomeRepository {

    override suspend fun obtenerBanner(): BrandConfig {
        return BrandCatalog.ACTIVA
    }

    override suspend fun obtenerUsuario() = Usuario(
        nombre = "Cristina",
        fotoUrl = "https://i.pravatar.cc/150?img=47"
    )

    override suspend fun obtenerCampaniaActiva() = Campania(
        id = "vacunacion_antirrabica",
        titulo = "Vacunación Antirrábica",
        subtitulo = "Gratuita hasta el 30 de junio",
        activa = true,
        imagen = R.drawable.ic_campaign,
        //imagenUrl = "https://picsum.photos/seed/vacuna/600/300"
    )

    override suspend fun obtenerAccionesRapidas() = listOf(
        QuickAction(
            "mis_mascotas",
            "Mis Mascotas",
            R.drawable.ic_pets,
            GreenLight,
            GreenPrimary
        ),
        QuickAction(
            "sacar_turno",
            "Sacar Turno",
            R.drawable.ic_calendar_add,
            PurpleSoft,
            PurplePrimary
        ),
        QuickAction(
            "adoptar",
            "Adoptar",
            R.drawable.ic_favorite,
            PinkSoft,
            RedHeart
        ),
        QuickAction(
            "centros_cercanos",
            "Centros Cercanos",
            R.drawable.ic_location_on,
            YellowBackLocation,
            YellowIconLocation
        ),
        QuickAction(
            "campanias",
            "Campañas",
            R.drawable.ic_campaign,
            BlueSoft,
            BlueIconCampaign        )
    )

    override suspend fun obtenerRecordatorios() = listOf(
        Recordatorio(
            id = "rec_1",
            titulo = "Vacuna antirrábica",
            descripcion = "Rocky necesita vacuna antirrábica en 10 días.",
            fechaLimite = "15 de julio",
            imageVector = Icons.Default.Vaccines
        ),
        Recordatorio(
            id = "rec_2",
            titulo = "Turno confirmado",
            descripcion = "Luna tiene turno para castración.",
            fechaLimite = "18 de julio",
            imageVector = Icons.Default.ContentCut
        )
    )

    override suspend fun obtenerNoticias(): List<Noticia> =
        listOf(
        Noticia(
            "noticia_1",
            "Nuevo centro de castración",
            "Se inauguró en el barrio Villa del Parque",
            "https://www.flaticon.es/icono-gratis/castracion_14665469"
        ),
        Noticia(
            "noticia_2",
            "Campaña de adopción",
            "Este sábado en la plaza principal",
            "https://www.pngwing.com/es/free-png-xumgk"
        )
    )

    override suspend fun getHomeData(): HomeUiState {

        return HomeUiState(
            cargando = false,

            usuario = Usuario(
                nombre = "Cristina",
                fotoUrl = null
            ),

            campania = Campania(
                id = "Campaña",
                titulo = "Campaña de Castración Gratuita",
                subtitulo = "Gratuita hasta el 30 de Junio",
                imagen = R.drawable.ic_campaign,
                activa = true,
            ),

            acciones = listOf(
                QuickAction(
                    id = "reportar",
                    titulo = "Reportar",
                    iconoRes = R.drawable.ic_report,
                    colorFondo = OrangeBackReport,
                    colorIcono = OrangeReportIcon
                ),
                QuickAction(
                    id = "turnos",
                    titulo = "Turnos",
                    iconoRes = R.drawable.ic_calendar_add,
                    colorFondo = PurpleSoft,
                    colorIcono = PurplePrimary
                ),
                QuickAction(
                    id = "adopciones",
                    titulo = "Adopciones",
                    iconoRes = R.drawable.ic_favorite,
                    colorFondo = PinkSoft,
                    colorIcono = RedHeart
                ),
                QuickAction(
                    id = "centros_cercanos",
                    titulo = "Centros Cercanos",
                    iconoRes = R.drawable.ic_location_on,
                    colorFondo = YellowBackLocation,
                    colorIcono = YellowIconLocation
                ),
                QuickAction(
                    id = "campanias",
                    titulo = "Proximas Campañas",
                    iconoRes = R.drawable.ic_campaign,
                    colorFondo = BlueSoft,
                    colorIcono = BlueIconCampaign
                )
            ),

            recordatorios = obtenerRecordatorios(),

            noticias = obtenerNoticias(),

            hayNotificaciones = true
        )
    }
}