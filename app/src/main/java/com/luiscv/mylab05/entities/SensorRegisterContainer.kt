package com.luiscv.mylab05.entities

import com.luiscv.mylab05.entities.SensorRegister

data class SensorRegisterContainer(
    val registers: List<SensorRegister>,
    val start: Int,
    val max: Int,
    val lastRegistroId: Int
    )
