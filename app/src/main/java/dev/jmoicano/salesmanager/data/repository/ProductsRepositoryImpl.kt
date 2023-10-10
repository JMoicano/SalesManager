package dev.jmoicano.salesmanager.data.repository

import dev.jmoicano.salesmanager.data.SalesDao
import dev.jmoicano.salesmanager.data.entities.Product
import dev.jmoicano.salesmanager.data.entities.Sale
import dev.jmoicano.salesmanager.data.entities.mapDto
import dev.jmoicano.salesmanager.ui.data.ViewProduct
import dev.jmoicano.salesmanager.ui.data.ViewSale

class ProductsRepositoryImpl(
    private val salesDao: SalesDao
) : ProductsRepository {
    override suspend fun insertSale(sale: ViewSale) {
        val saleId = salesDao.insertSale(
            Sale(
                client = sale.client
            )
        )
        salesDao.insertProducts(sale.products.map {
            Product(
                name = it.name,
                quant = it.quant,
                unitPrice = it.unitPrice,
                saleId = saleId
            )
        })
    }

    override suspend fun getSaleId(): Long {
        return salesDao.getLastId() + 1
    }

}