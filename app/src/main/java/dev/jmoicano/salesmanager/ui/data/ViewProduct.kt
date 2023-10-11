package dev.jmoicano.salesmanager.ui.data

data class ViewProduct(
    val name: String,
    val quant: Double,
    val unitPrice: Double,
    var discount: Double = 0.0
) {
    val totalValue
        get() = quant * unitPrice
    val finalValue
        get() = (quant * unitPrice) - discount
}
