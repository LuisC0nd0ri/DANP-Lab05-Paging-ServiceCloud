package com.luiscv.mylab05.operations

data class SensorRegisterContainer(
    val registers: List<SensorRegister>,
    val start: Int,
    val max: Int,
    val lastRegistroId: Int
    )
