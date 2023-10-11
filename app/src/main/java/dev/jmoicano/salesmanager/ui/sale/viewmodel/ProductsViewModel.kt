package dev.jmoicano.salesmanager.ui.sale.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jmoicano.salesmanager.data.repository.ProductsRepository
import dev.jmoicano.salesmanager.ui.data.ViewProduct
import dev.jmoicano.salesmanager.ui.data.ViewSale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val productsRepository: ProductsRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    var clientName by mutableStateOf("")
        private set

    var productName by mutableStateOf("")
        private set

    var productQuant by mutableStateOf("")
        private set

    var productPrice by mutableStateOf("")
        private set

    var saleDiscount by mutableStateOf("")
        private set

    var itemPrice by mutableDoubleStateOf(
        (productQuant.toDoubleOrNull() ?: 0.0) * (productPrice.toDoubleOrNull() ?: 0.0)
    )

    fun updateClientName(name: String) {
        clientName = name
    }

    fun updateProductName(name: String) {
        productName = name
    }

    fun updateQuant(quant: String) {
        productQuant = quant
        itemPrice = (productQuant.toDoubleOrNull() ?: 0.0) * (productPrice.toDoubleOrNull() ?: 0.0)
    }

    fun updatePrice(price: String) {
        productPrice = price
        itemPrice = (productQuant.toDoubleOrNull() ?: 0.0) * (productPrice.toDoubleOrNull() ?: 0.0)
    }

    fun updateDiscount(discount: String) {
        saleDiscount = discount
    }

    fun resetSale() {
        clientName = ""
        saleDiscount = ""
        resetFields()
        _uiState.update {
            ProductUiState()
        }
    }

    private fun resetFields() {
        productName = ""
        productQuant = ""
        productPrice = ""
        itemPrice = 0.0
    }


    fun saveSale() {
        viewModelScope.launch(context = Dispatchers.IO) {
            productsRepository.insertSale(
                ViewSale(
                    client = clientName,
                    products = _uiState.value.products,
                    discount = _uiState.value.discount
                )
            )
            resetSale()
        }
    }

    fun applyDiscount() {
        _uiState.update {currentState ->
            currentState.copy(
                discount = saleDiscount.toDouble(),
            )
        }
        rateDiscount()
    }

    private fun rateDiscount() {
        val currentProducts = _uiState.value.products.toMutableList()
        _uiState.update {currentState ->
            currentState.copy(
                products = currentProducts.onEach {
                    val productRate = it.totalValue / _uiState.value.totalPrice
                    it.discount = _uiState.value.discount * productRate
                }
            )
        }
    }

    fun includeItem() {
        _uiState.update { currentState ->
            val currentProducts = currentState.products.toMutableStateList()
            currentState.copy(
                products = currentProducts.apply {
                    add(
                        ViewProduct(
                            name = productName,
                            quant = productQuant.toDouble(),
                            unitPrice = productPrice.toDouble()
                        )
                    )
                }
            )
        }
        rateDiscount()
        resetFields()
    }

    fun getSaleNumber() {
        viewModelScope.launch(context = Dispatchers.IO) {
            val saleNumber = productsRepository.getSaleId()
            _uiState.update { currentState ->
                currentState.copy(saleId = saleNumber)
            }
        }
    }
}