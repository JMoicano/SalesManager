package dev.jmoicano.salesmanager.di

import android.content.Context
import androidx.room.Room
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
        ).build()

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