package dev.jmoicano.salesmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.jmoicano.salesmanager.data.entities.Product
import dev.jmoicano.salesmanager.data.entities.Sale

@Database(entities = [Sale::class, Product::class], version = 1)
abstract class SalesDatabase : RoomDatabase() {
    abstract fun salesDao(): SalesDao
}