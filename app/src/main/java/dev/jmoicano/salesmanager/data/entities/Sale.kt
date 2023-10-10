package dev.jmoicano.salesmanager.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import dev.jmoicano.salesmanager.ui.data.ViewProduct
import dev.jmoicano.salesmanager.ui.data.ViewSale

@Entity
data class Sale(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "client") val client: String
)

data class SaleWithProducts(
    @Embedded val sale: Sale,
    @Relation(
        parentColumn = "id",
        entityColumn = "saleId"
    )
    val products: List<Product>
)

fun SaleWithProducts.mapDto(): ViewSale =
    ViewSale(client = sale.client, products = products.map { it.mapDto() })