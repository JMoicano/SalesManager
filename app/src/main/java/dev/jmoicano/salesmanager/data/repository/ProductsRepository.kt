package dev.jmoicano.salesmanager.data.repository

import dev.jmoicano.salesmanager.ui.data.ViewSale

interface ProductsRepository {
    suspend fun insertSale(sale: ViewSale)
    suspend fun getSaleId(): Long
}