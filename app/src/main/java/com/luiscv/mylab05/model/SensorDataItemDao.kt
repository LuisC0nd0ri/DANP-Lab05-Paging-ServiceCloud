package com.luiscv.mylab05.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luiscv.mylab05.entities.SensorDataItem

//todo: ROOM===================================================================


@Dao
interface SensorDataItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sensorData : List<SensorDataItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sensorDataItem: SensorDataItem)

    @Query("SELECT * FROM sensordataitem")
    fun getAllSensorData(): List<SensorDataItem>

    @Query("DELETE FROM sensordataitem")
    suspend fun clearAll()
}