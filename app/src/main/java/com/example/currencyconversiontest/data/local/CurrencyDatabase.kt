package com.example.currencyconversiontest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyconversiontest.data.local.dao.CurrencyDao
import com.example.currencyconversiontest.model.CurrencyModel
import com.example.currencyconversiontest.model.CurrencyRatesModel

@Database(
    entities = [CurrencyModel::class, CurrencyRatesModel::class],
    version = 1,
    exportSchema = false
)
abstract class CurrencyDatabase : RoomDatabase() {


    abstract fun getCurrencyDao(): CurrencyDao

    companion object {
        const val DB_NAME = "currency_database"

        @Volatile
        private var INSTANCE: CurrencyDatabase? = null

        fun getInstance(context: Context): CurrencyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}