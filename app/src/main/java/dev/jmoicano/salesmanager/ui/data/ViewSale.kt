package dev.jmoicano.salesmanager.ui.data

data class ViewSale(
    val client: String,
    val products: List<ViewProduct>
) {
    val salesTotal: Double
        get() = products.sumOf { product -> product.totalValue }
}
