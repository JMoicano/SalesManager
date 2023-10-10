package dev.jmoicano.salesmanager.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import dev.jmoicano.salesmanager.data.entities.Product
import dev.jmoicano.salesmanager.data.entities.Sale
import dev.jmoicano.salesmanager.data.entities.SaleWithProducts

@Dao
interface SalesDao {
    @Transaction
    @Query("SELECT * FROM Sale")
    fun getAllSalesWithProducts(): List<SaleWithProducts>

    @Insert
    fun insertSale(sale: Sale): Long

    @Insert
    fun insertProducts(products: List<Product>)

    @Query("SELECT MAX(id) FROM Sale")
    fun getLastId(): Long
}