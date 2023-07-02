package com.luiscv.mylab05.model_obsolete

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luiscv.mylab05.entities_obsolete.SensorDataItem

//todo: ROOM===================================================================


@Dao
interface SensorDataItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sensorData : List<SensorDataItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sensorDataItem: SensorDataItem)

    @Query("SELECT * FROM sensordataitem")
    fun getAllSensorData(): List<SensorDataItem>

    //devuelve elementos para una determinada pagina
    @Query("SELECT * FROM sensordataitem ORDER BY id LIMIT :pageSize OFFSET :offset")
    fun getItemsByPage(pageSize: Int, offset: Int): List<SensorDataItem>

    //devuelve el numero total de elementos
    @Query("SELECT COUNT(*) FROM sensordataitem")
    suspend fun getTotalItemCount(): Int

    suspend fun isTableEmpty(): Boolean {
        return getTotalItemCount() == 0
    }

    @Query("DELETE FROM sensordataitem")
    suspend fun clearAll()
}