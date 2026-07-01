package com.example.tpinterfaces.branding

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class BrandConfig(
    val municipioId: String,
    val nombreMunicipio: String,
    val colorPrimario: Color,
    @DrawableRes val bannerRes: Int,      // la imagen completa del cartel (la que subiste)
    @DrawableRes val isotipoRes: Int? = null, // logo suelto, opcional, para otras pantallas
    val nombreApp: String = "Bienestar Animal"
)