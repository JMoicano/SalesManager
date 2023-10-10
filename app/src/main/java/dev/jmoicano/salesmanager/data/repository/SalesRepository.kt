package dev.jmoicano.salesmanager.data.repository

import dev.jmoicano.salesmanager.ui.data.ViewProduct
import dev.jmoicano.salesmanager.ui.data.ViewSale

interface SalesRepository {
    suspend fun getSales(): List<ViewSale>
    suspend fun createSale(sale: ViewSale)
}