package dev.jmoicano.salesmanager.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jmoicano.salesmanager.data.repository.SalesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(private val salesRepository: SalesRepository): ViewModel() {
    private val _salesUiState = MutableStateFlow(SalesUiState())
    val salesUiState: StateFlow<SalesUiState> = _salesUiState.asStateFlow()

    init {
        getSales()
    }

    fun getSales() {
        viewModelScope.launch(context = Dispatchers.IO) {
            val sales = salesRepository.getSales()
            _salesUiState.update { currentState ->
                currentState.copy(sales = sales)
            }
        }
    }

}