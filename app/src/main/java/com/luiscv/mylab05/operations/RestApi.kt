package com.luiscv.mylab05.operations

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface RestApi {
    @Headers("Content-Type: application/json")
    @POST("sensorapi")
    fun crearRegistro(
        @Body sensorinfo: SensorRegister?
    ): Call<SensorRegister?>?
    @GET("sensorapi")
    fun obtenerKeyMax(
        @Query("startregister") start: Int,
        @Query("maxregisters") max: Int
    ): Call<SensorRegisterContainer>
    @GET("sensorapi")
    fun obtenerRegistros(
        @Query("startregister") start: Int,
        @Query("maxregisters") max: Int
    ): Call<SensorRegisterContainer>
}