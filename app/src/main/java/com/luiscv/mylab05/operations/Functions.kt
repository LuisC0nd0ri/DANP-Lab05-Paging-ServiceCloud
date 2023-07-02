package com.luiscv.mylab05.operations

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

fun getData_Retrofit_all(startregister: Int, maxregisters:Int): SensorRegisterContainer? {

    val url = "https://qap9opok49.execute-api.us-west-2.amazonaws.com/prod/"
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitAPI = retrofit.create(RestApi::class.java)

    try {
        val response = retrofitAPI.obtenerRegistros(startregister, maxregisters).execute()
        if (response.isSuccessful) {
            val registersResponse = response.body()
            val lastRegistroId = registersResponse
            if (lastRegistroId != null) {
                Log.d("GET_ALL_REGISTROS", lastRegistroId.registers.toString())
            }
            return lastRegistroId
        } else {
            val errorCode = response.code()
            Log.d("GET_ALL_ERROR_CODE", "Código de error: $errorCode")
        }
    } catch (e: IOException) {
        Log.d("GET_ALL_IO_EX", "error")
    }

    return null
}