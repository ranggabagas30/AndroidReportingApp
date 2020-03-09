package com.domikado.bit.abstraction.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.domikado.bit.BuildConfig
import com.domikado.bit.data.local.database.dao.*
import com.domikado.bit.data.local.database.entity.*

private const val DATABASE_SCHEMA_VERSION = 1

@Database(
    entities = [
        TbOperator::class,
        TbSchedule::class,
        TbSite::class,
        TbUser::class,
        TbWorkForm::class,
        TbWorkFormColumn::class,
        TbWorkFormGroup::class,
        TbWorkFormItem::class,
        TbWorkFormRow::class,
        TbWorkType::class,
        TbJoinScheduleOperator::class,
        TbJoinScheduleSite::class,
        TbJoinWorkFormRowColumn::class
    ],
    version = DATABASE_SCHEMA_VERSION,
    exportSchema = true
)
abstract class BitDatabase : RoomDatabase() {

    // database access objects
    abstract fun itemDao(): ItemDao
    abstract fun rowDao(): RowDao
    abstract fun rowColumnDao(): RowColumnJoinDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun scheduleOperatorJoinDao(): ScheduleOperatorJoinDao
    abstract fun scheduleSiteJoinDao(): ScheduleSiteJoinDao
    abstract fun workTypeDao(): WorkTypeDao

    // database singleton
    companion object {

        @Volatile private var INSTANCE: BitDatabase? = null

        fun getInstance(context: Context): BitDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(
                        context
                    ).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BitDatabase::class.java,
                BuildConfig.DATABASE_NAME
            ).build()

    }
}