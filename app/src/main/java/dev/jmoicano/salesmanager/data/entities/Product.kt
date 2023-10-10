package dev.jmoicano.salesmanager.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.jmoicano.salesmanager.ui.data.ViewProduct

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val quant: Int,
    val unitPrice: Double,
    val saleId: Long
)

fun Product.mapDto(): ViewProduct = ViewProduct(name, quant, unitPrice)
