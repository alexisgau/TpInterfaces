package com.example.tpinterfaces.data.repository

import com.example.tpinterfaces.branding.BrandConfig
import com.example.tpinterfaces.data.model.Campania
import com.example.tpinterfaces.data.model.Noticia
import com.example.tpinterfaces.data.model.QuickAction
import com.example.tpinterfaces.data.model.Recordatorio
import com.example.tpinterfaces.data.model.Usuario
import com.example.tpinterfaces.ui.viewModel.HomeUiState

interface HomeRepository {
    suspend fun obtenerBanner(): BrandConfig
    suspend fun obtenerUsuario(): Usuario
    suspend fun obtenerCampaniaActiva(): Campania?
    suspend fun obtenerAccionesRapidas(): List<QuickAction>
    suspend fun obtenerRecordatorios(): List<Recordatorio>
    suspend fun obtenerNoticias(): List<Noticia>
    suspend fun getHomeData(): HomeUiState
}