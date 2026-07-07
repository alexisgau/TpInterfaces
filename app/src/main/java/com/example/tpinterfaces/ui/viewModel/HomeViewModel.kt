package com.example.tpinterfaces.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tpinterfaces.data.model.Campania
import com.example.tpinterfaces.data.model.Noticia
import com.example.tpinterfaces.data.model.QuickAction
import com.example.tpinterfaces.data.model.Recordatorio
import com.example.tpinterfaces.data.model.Usuario
import com.example.tpinterfaces.data.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(cargando = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        cargarHome()
    }

    private fun cargarHome() {
        viewModelScope.launch {
            try {
                _uiState.value = repository.getHomeData()
            } catch (e: Exception) {
                _uiState.value = HomeUiState(
                    cargando = false
                )
            }
        }
    }

    fun onNotificationClick() {
        // TODO Navegar a notificaciones
    }

    fun onAccionRapidaClick(actionId: String) {
        when (actionId) {
            "reportar" -> {
                // TODO
            }

            "turnos" -> {
                // TODO
            }

            "mis_reportes" -> {
                // TODO
            }

            else -> Unit
        }
    }
}

data class HomeUiState(
    val cargando: Boolean = false,
    val usuario: Usuario? = null,
    val hayNotificaciones: Boolean = false,
    val campania: Campania? = null,
    val acciones: List<QuickAction> = emptyList(),
    val recordatorios: List<Recordatorio> = emptyList(),
    val noticias: List<Noticia> = emptyList()
)