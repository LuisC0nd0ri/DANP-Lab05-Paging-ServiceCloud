package com.luiscv.mylab05.operations

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.luiscv.mylab05.MainActivity
import com.luiscv.mylab05.entities.SensorDataItem
import com.luiscv.mylab05.model.SensorDataItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

@Composable
fun addSensorDataItem(
    showDialogDataRegister: MutableState<Boolean>,
    dao: SensorDataItemDao,
    context: Context,
    mainActivity: MainActivity
){

    //campos
    var tffecha by remember { mutableStateOf("") }
    var tfmedicionTemperatura by remember { mutableStateOf("") }
    var tfcomentario by remember { mutableStateOf("") }
    val ctx = LocalContext.current

    if(showDialogDataRegister.value){
        AlertDialog(
            onDismissRequest = { /*TODO*/ showDialogDataRegister.value = false },
            confirmButton = {
                TextButton(onClick = { /*TODO*/
                    //AQUI AGREGAMOS EL CODIGO PARA REGISTRAR

                    //para que funcione con suspend
                    runBlocking {
                        withContext(Dispatchers.IO) {
                            val lastRegistroId: Int? = getData_Retrofit_lastkey(ctx)
                            if (lastRegistroId != null) {
                                postData_Retrofit(
                                    ctx,lastRegistroId+1,tffecha.toString(),tfmedicionTemperatura.toInt(),tfcomentario.toString()
                                )
                            }
                            dao.insert(
                                SensorDataItem(
                                0,tffecha.toString(),tfmedicionTemperatura.toString(),tfcomentario.toString())
                            )
                        }
                    }

                    //cerrar ventana
                    showDialogDataRegister.value = false

                    val intent = Intent(context, MainActivity::class.java)
                    mainActivity.startActivity(intent)

                }) {
                    Text(text = "Registrar")
                }
            },
            dismissButton = {
                TextButton(onClick = { /*TODO*/ showDialogDataRegister.value = false }) {
                    Text(text = "Cancelar")
                }
            },
            title = { Text(text = "Registrar datos de sensor") },
            text = {
                Column() {
                    Text(text = "Registre los datos", modifier = Modifier.padding(bottom = 10.dp))
                    TextField(value = tffecha,
                        onValueChange = { tffecha = it }, placeholder = { Text(text = "Fecha") })
                    TextField(value = tfmedicionTemperatura,
                        onValueChange = { tfmedicionTemperatura = it }, placeholder = { Text(text = "Medicion Temperatura") })
                    TextField(value = tfcomentario,
                        onValueChange = { tfcomentario = it }, placeholder = { Text(text = "Comentario") })
                }
            }
        )
    }

}

private fun postData_Retrofit(
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

private suspend fun getData_Retrofit_lastkey(ctx: Context): Int? {
    val url = "https://qap9opok49.execute-api.us-west-2.amazonaws.com/prod/"
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitAPI = retrofit.create(RestApi::class.java)

    val startregister = 1
    val maxregisters = 20

    try {
        val response = retrofitAPI.obtenerRegistros(startregister, maxregisters).execute()
        if (response.isSuccessful) {
            val registersResponse = response.body()
            val lastRegistroId = registersResponse?.lastRegistroId
            Log.d("POST_GET", lastRegistroId.toString())
            return lastRegistroId
        } else {
            // Procesar respuesta no exitosa
        }
    } catch (e: IOException) {
        // Procesar error en la petición
    }

    return null
}