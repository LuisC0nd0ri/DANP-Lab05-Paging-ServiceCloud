package com.luiscv.mylab05.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luiscv.mylab05.entities.SensorDataItem

//todo: ROOM===================================================================

@Database(entities = [SensorDataItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun operationsSesnsorDataItemDao(): SensorDataItemDao
}