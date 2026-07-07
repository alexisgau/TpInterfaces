package com.example.tpinterfaces.branding

import androidx.compose.runtime.compositionLocalOf

val LocalBrand = compositionLocalOf<BrandConfig> {
    error("No hay BrandConfig provisto. Envolvé tu UI con BienestarAnimalTheme(brand = ...) { ... }")
}