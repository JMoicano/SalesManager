package dev.jmoicano.salesmanager.data.repository

import dev.jmoicano.salesmanager.data.SalesDao
import dev.jmoicano.salesmanager.data.entities.Sale
import dev.jmoicano.salesmanager.data.entities.mapDto
import dev.jmoicano.salesmanager.ui.data.ViewProduct
import dev.jmoicano.salesmanager.ui.data.ViewSale

class SalesRepositoryImpl(
    private val salesDao: SalesDao
) : SalesRepository {
    override suspend fun getSales(): List<ViewSale> {
        return salesDao.getAllSalesWithProducts().map {
            it.mapDto()
        }
    }

    override suspend fun createSale(sale: ViewSale) {
        salesDao.insertSale(Sale(client = sale.client, discount = sale.discount))
    }
}