package com.luiscv.mylab05.entities_obsolete

import androidx.room.Entity
import androidx.room.PrimaryKey

//todo: ROOM===================================================================

@Entity(tableName = "sensordataitem")
data class SensorDataItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fecha: String,
    val medicionTemperatura: String,
    val comentario: String
    )