package com.example.tpinterfaces.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tpinterfaces.data.model.EspecieMascota
import com.example.tpinterfaces.data.model.Mascota
import com.example.tpinterfaces.data.model.SexoMascota
import com.example.tpinterfaces.data.repository.MascotasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class AgregarMascotaViewModel(
    private val repository: MascotasRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AgregarMascotaUiState())
    val uiState: StateFlow<AgregarMascotaUiState> = _uiState.asStateFlow()

    fun onNombreChange(valor: String) { _uiState.value = _uiState.value.copy(nombre = valor) }
    fun onEspecieChange(valor: EspecieMascota) { _uiState.value = _uiState.value.copy(especie = valor) }
    fun onRazaChange(valor: String) { _uiState.value = _uiState.value.copy(raza = valor) }
    fun onEdadChange(valor: String) { _uiState.value = _uiState.value.copy(edadAnios = valor) }
    fun onPesoChange(valor: String) { _uiState.value = _uiState.value.copy(pesoKg = valor) }
    fun onSexoChange(valor: SexoMascota) { _uiState.value = _uiState.value.copy(sexo = valor) }
    fun onCastradoChange(valor: Boolean) { _uiState.value = _uiState.value.copy(castrado = valor) }
    fun onAlergiaInputChange(valor: String) { _uiState.value = _uiState.value.copy(alergiaInput = valor) }

    fun agregarAlergia() {
        val input = _uiState.value.alergiaInput.trim()
        if (input.isNotBlank() && !_uiState.value.alergias.contains(input)) {
            _uiState.value = _uiState.value.copy(
                alergias = _uiState.value.alergias + input,
                alergiaInput = ""
            )
        }
    }

    fun quitarAlergia(alergia: String) {
        _uiState.value = _uiState.value.copy(alergias = _uiState.value.alergias - alergia)
    }

    fun guardar() {
        val s = _uiState.value
        if (!s.formularioValido) return
        viewModelScope.launch {
            _uiState.value = s.copy(guardando = true)
            val mascota = Mascota(
                id = UUID.randomUUID().toString(),
                nombre = s.nombre.trim(),
                especie = s.especie!!,
                raza = s.raza.trim(),
                edadAnios = s.edadAnios.toIntOrNull() ?: 0,
                pesoKg = s.pesoKg.toDoubleOrNull() ?: 0.0,
                sexo = s.sexo!!,
                castrado = s.castrado,
                fotoRes = null,
                alergias = s.alergias
            )
            val ok = repository.agregarMascota(mascota)
            _uiState.value = _uiState.value.copy(guardando = false, guardadoExitoso = ok)
        }
    }
}

class AgregarMascotaViewModelFactory(
    private val repository: MascotasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AgregarMascotaViewModel(repository) as T
    }
}

data class AgregarMascotaUiState(
    val nombre: String = "",
    val especie: EspecieMascota? = null,
    val raza: String = "",
    val edadAnios: String = "",
    val pesoKg: String = "",
    val sexo: SexoMascota? = null,
    val castrado: Boolean = false,
    val alergiaInput: String = "",
    val alergias: List<String> = emptyList(),
    val guardando: Boolean = false,
    val guardadoExitoso: Boolean = false,
    val error: String? = null
) {
    val formularioValido: Boolean
        get() = nombre.isNotBlank() && especie != null && raza.isNotBlank() && edadAnios.isNotBlank() && pesoKg.isNotBlank() && sexo != null
}