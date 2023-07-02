package com.luiscv.mylab05.operations

import android.content.Context
import android.util.Log
import com.luiscv.mylab05.api.RestApi
import com.luiscv.mylab05.entities.SensorRegister
import com.luiscv.mylab05.entities.SensorRegisterContainer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

fun postData_Retrofit(
    ctx: Context,
    RegistroId: Int,
    FechayHora: String,
    medida: Int,
    comentario: String
) {
    var url = "https://qap9opok49.execute-api.us-west-2.amazonaws.com/prod/"
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitAPI = retrofit.create(RestApi::class.java)
    val dataModel = SensorRegister(RegistroId, FechayHora,medida,comentario)
    val call: Call<SensorRegister?>? = retrofitAPI.crearRegistro(dataModel)
    call!!.enqueue(object : Callback<SensorRegister?> {
        override fun onResponse(call: Call<SensorRegister?>?, response: Response<SensorRegister?>) {
            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d("POST_SUCCESS", "Respuesta exitosa")
            } else {
                val errorCode = response.code()
                Log.d("POST_ERROR", "Código de error: $errorCode")
            }
        }

        override fun onFailure(call: Call<SensorRegister?>?, t: Throwable) {
            Log.e("POST_FAILURE", "Error en la solicitud: ${t.message}")
        }
    })
}

fun getData_Retrofit_lastkey(ctx: Context): Int? {
    val url = "https://qap9opok49.execute-api.us-west-2.amazonaws.com/prod/"
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitAPI = retrofit.create(RestApi::class.java)

    val startregister = 1
    val maxregisters = 20

    try {
        val response = retrofitAPI.obtenerKeyMax(startregister, maxregisters).execute()
        if (response.isSuccessful) {
            val registersResponse = response.body()
            val lastRegistroId = registersResponse?.lastRegistroId
            Log.d("GET_REGISTROID", lastRegistroId.toString())
            return lastRegistroId
        } else {
            val errorCode = response.code()
            Log.d("GET_ERROR", "Código de error: $errorCode")
        }
    } catch (e: IOException) {
    }

    return null
}