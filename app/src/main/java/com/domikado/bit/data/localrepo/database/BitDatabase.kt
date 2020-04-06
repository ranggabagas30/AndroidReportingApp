package com.domikado.bit.data.localrepo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.domikado.bit.BuildConfig
import com.domikado.bit.data.localrepo.database.dao.*
import com.domikado.bit.data.localrepo.database.entity.*

@Database(
    entities = [
        TbOperator::class,
        TbSchedule::class,
        TbWorkType::class,
        TbSite::class,
        TbUser::class,
        TbFormFillData::class,
//        TbWorkForm::class,
//        TbWorkFormColumn::class,
//        TbWorkFormGroup::class,
//        TbWorkFormItem::class,
//        TbWorkFormRow::class,
//        TbWorkType::class,
        TbJoinScheduleOperator::class,
        TbJoinSiteMonitoring::class
        //TbJoinWorkFormRowColumn::class
    ],
    version = DATABASE_SCHEMA_VERSION,
    exportSchema = false
)
abstract class BitDatabase : RoomDatabase() {

    // database access objects
//    abstract fun itemDao(): ItemDao
//    abstract fun rowDao(): RowDao
//    abstract fun rowColumnDao(): RowColumnJoinDao
    abstract fun userDao(): UserDao
    abstract fun operatorDao(): OperatorDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun scheduleOperatorJoinDao(): ScheduleOperatorJoinDao
    abstract fun scheduleSiteJoinDao(): ScheduleSiteJoinDao
    abstract fun workTypeDao(): WorkTypeDao
    abstract fun formFillDataDao(): FormFillDataDao

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

private const val DATABASE_SCHEMA_VERSION = 1
