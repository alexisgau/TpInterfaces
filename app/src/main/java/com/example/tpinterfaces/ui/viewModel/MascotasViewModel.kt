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

class MascotasViewModel(private val repository: MascotasRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MascotasUiState())
    val uiState: StateFlow<MascotasUiState> = _uiState.asStateFlow()

    init { cargarMascotas() }

    private fun cargarMascotas() {
        viewModelScope.launch {
            try {
                val mascotas = repository.obtenerMascotas()
                _uiState.value = MascotasUiState(cargando = false, mascotas = mascotas)
            } catch (e: Exception) {
                _uiState.value = MascotasUiState(cargando = false, error = e.message)
            }
        }
    }
}

class MascotasViewModelFactory(private val repository: MascotasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MascotasViewModel(repository) as T
    }
}

data class MascotasUiState(
    val cargando: Boolean = true,
    val mascotas: List<Mascota> = emptyList(),
    val error: String? = null
)