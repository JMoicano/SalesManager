package dev.jmoicano.salesmanager.ui.sale.viewmodel

import dev.jmoicano.salesmanager.ui.data.ViewProduct

data class ProductUiState(
    val products: List<ViewProduct> = listOf(),
    val saleId: Long = 0
) {
    val totalQuant: Int = products.sumOf { it.quant }
    val totalPrice: Double = products.sumOf { it.totalValue }
}
