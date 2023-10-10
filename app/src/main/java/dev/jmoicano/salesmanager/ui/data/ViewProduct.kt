package dev.jmoicano.salesmanager.ui.data

data class ViewProduct(
    val name: String,
    val quant: Int,
    val unitPrice: Double,
) {
    val totalValue
        get() = quant * unitPrice
}
