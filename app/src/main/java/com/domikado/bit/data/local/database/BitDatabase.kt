package com.domikado.bit.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class BitDatabase : RoomDatabase() {

    // database singleton
    companion object {

        @Volatile private var INSTANCE: BitDatabase? = null

        fun getInstance(context: Context): BitDatabase =
            INSTANCE?: synchronized(this) {
                INSTANCE?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BitDatabase::class.java,
                "_main.db"
            ).build()

    }
}