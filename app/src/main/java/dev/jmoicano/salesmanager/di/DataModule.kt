package dev.jmoicano.salesmanager.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.jmoicano.salesmanager.data.SalesDao
import dev.jmoicano.salesmanager.data.SalesDatabase
import dev.jmoicano.salesmanager.data.repository.ProductsRepository
import dev.jmoicano.salesmanager.data.repository.ProductsRepositoryImpl
import dev.jmoicano.salesmanager.data.repository.SalesRepository
import dev.jmoicano.salesmanager.data.repository.SalesRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun providesDatabase(@ApplicationContext applicationContext: Context): SalesDatabase =
        Room.databaseBuilder(
            applicationContext,
            SalesDatabase::class.java,
            "db-sales"
        )
            .addMigrations(
                object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("DROP TABLE IF EXISTS Product_old;")
                        database.execSQL("ALTER TABLE Product RENAME TO Product_old")
                        database.execSQL("CREATE TABLE IF NOT EXISTS `Product` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `quant` REAL NOT NULL, `unitPrice` REAL NOT NULL, `saleId` INTEGER NOT NULL)")
                        database.execSQL("INSERT INTO Product SELECT * FROM Product_old")
                        database.execSQL("DROP TABLE IF EXISTS Product_old")
                    }
                },
                object : Migration(2, 3) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("ALTER TABLE Sale ADD COLUMN discount REAL NOT NULL DEFAULT 0.0")
                    }
                })

            .build()

    @Provides
    fun providesSalesDao(salesDatabase: SalesDatabase): SalesDao {
        return salesDatabase.salesDao()
    }

    @Provides
    fun providesSalesRepository(
        salesDao: SalesDao
    ): SalesRepository =
        SalesRepositoryImpl(
            salesDao = salesDao
        )

    @Provides
    fun providesProductsRepository(
        salesDao: SalesDao
    ): ProductsRepository =
        ProductsRepositoryImpl(
            salesDao = salesDao
        )
}