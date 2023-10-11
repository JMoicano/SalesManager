package dev.jmoicano.salesmanager.ui.data

data class ViewSale(
    val client: String,
    val products: List<ViewProduct>,
    val discount: Double
) {
    val salesTotal: Double
        get() = products.sumOf { product -> product.totalValue }

    val salesFinal: Double
        get() = salesTotal - discount
}
