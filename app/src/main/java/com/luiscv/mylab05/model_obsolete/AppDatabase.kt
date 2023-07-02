package com.luiscv.mylab05.model_obsolete

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luiscv.mylab05.entities_obsolete.SensorDataItem

//todo: ROOM===================================================================

@Database(entities = [SensorDataItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun operationsSesnsorDataItemDao(): SensorDataItemDao
}