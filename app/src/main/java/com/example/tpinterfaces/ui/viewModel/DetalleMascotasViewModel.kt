package com.example.tpinterfaces.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tpinterfaces.data.model.Mascota
import com.example.tpinterfaces.data.repository.MascotasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetalleMascotaViewModel(
    private val mascotaId: String,
    private val repository: MascotasRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleMascotaUiState())
    val uiState: StateFlow<DetalleMascotaUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val mascota = repository.obtenerMascota(mascotaId)
            _uiState.value = DetalleMascotaUiState(
                cargando = false,
                mascota = mascota
            )
        }
    }
}

class DetalleMascotaViewModelFactory(
    private val mascotaId: String,
    private val repository: MascotasRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return DetalleMascotaViewModel(mascotaId, repository) as T
    }
}

data class DetalleMascotaUiState(
    val cargando: Boolean = true,
    val mascota: Mascota? = null,
    val error: String? = null
)