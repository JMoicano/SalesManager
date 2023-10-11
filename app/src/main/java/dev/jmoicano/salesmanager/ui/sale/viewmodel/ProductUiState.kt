package dev.jmoicano.salesmanager.ui.sale.viewmodel

import dev.jmoicano.salesmanager.ui.data.ViewProduct

data class ProductUiState(
    val products: List<ViewProduct> = listOf(),
    val saleId: Long = 0,
    val discount: Double = 0.0
) {
    val totalQuant: Double = products.sumOf { it.quant }
    val totalPrice: Double = products.sumOf { it.totalValue }
    val finalPrice: Double = products.sumOf { it.totalValue }  - discount
}
