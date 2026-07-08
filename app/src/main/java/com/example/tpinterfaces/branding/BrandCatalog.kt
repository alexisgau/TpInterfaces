package com.example.tpinterfaces.branding

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.tpinterfaces.R

object BrandCatalog {
    private var municipioActivo = "moron"
    val MORON = BrandConfig(
        municipioId = "moron",
        nombreMunicipio = "Municipio de Morón",
        colorPrimario = Color(0xFFE30613),
        bannerRes = R.drawable.banner_municipio_moron // poné el png que te paso abajo
    )

    // Ejemplo de otro cliente: mismo front, distinta marca
    val EJEMPLO = BrandConfig(
        municipioId = "ejemplo",
        nombreMunicipio = "Municipio de Ejemplo",
        colorPrimario = Color(0xFF1565C0),
        bannerRes = R.drawable.banner_municipio_ejemplo
    )

    private val brands = listOf(
        MORON,
        EJEMPLO
    )

    // En producción esto sale de un flavor de Gradle, BuildConfig o remote config
    val ACTIVA: BrandConfig
        get() = brands.firstOrNull {
            it.municipioId == municipioActivo
        } ?: MORON

    fun seleccionarMunicipio(id: String) {
        municipioActivo = id
    }
}