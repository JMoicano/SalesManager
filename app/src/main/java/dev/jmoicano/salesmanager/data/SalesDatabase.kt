package dev.jmoicano.salesmanager.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import dev.jmoicano.salesmanager.data.entities.Product
import dev.jmoicano.salesmanager.data.entities.Sale

@Database(entities = [Sale::class, Product::class], version = 3)
abstract class SalesDatabase : RoomDatabase() {
    abstract fun salesDao(): SalesDao

    class SalesAutoMigration: AutoMigrationSpec
}