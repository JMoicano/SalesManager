package dev.jmoicano.salesmanager.ui.home.viewmodel

import dev.jmoicano.salesmanager.ui.data.ViewSale

data class SalesUiState(
    val sales: List<ViewSale> = listOf()
) {
    val totalEarnings: Double =
        sales.sumOf { it.salesTotal }
}
