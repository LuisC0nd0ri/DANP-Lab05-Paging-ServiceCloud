package com.luiscv.mylab05.operations

import android.content.Context
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
import androidx.room.Room
import com.luiscv.mylab05.entities.SensorDataItem
import com.luiscv.mylab05.listaNuevaSensorData
import com.luiscv.mylab05.listaSensorData
import com.luiscv.mylab05.model.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Composable
fun addSensorDataItem(
    showDialogDataRegister: MutableState<Boolean>
){

    //campos
    var tffecha by remember { mutableStateOf("") }
    var tfmedicionTemperatura by remember { mutableStateOf("") }
    var tfcomentario by remember { mutableStateOf("") }


    //Jetpack Room============================
    val context = LocalContext.current
    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "sensor-db"
        ).build()
    }
    val dao = db.operationsSesnsorDataItemDao()
    //===============================================

    if(showDialogDataRegister.value){
        AlertDialog(
            onDismissRequest = { /*TODO*/ showDialogDataRegister.value = false },
            confirmButton = {
                TextButton(onClick = { /*TODO*/
                    //AQUI AGREGAMOS EL CODIGO PARA REGISTRAR

                    //todo: añadir al room tambien
                    //para que funcione con suspend
                    runBlocking {
                        withContext(Dispatchers.IO) {
                            dao.insert(
                                SensorDataItem(
                                0,tffecha.toString(),tfmedicionTemperatura.toString(),tfcomentario.toString())
                            )
                        }
                    }

                    //cerrar ventana
                    showDialogDataRegister.value = false

                    //agregar a lista
                    listaNuevaSensorData += SensorDataItem(
                        0,tffecha.toString(),tfmedicionTemperatura.toString(),tfcomentario.toString()
                    )

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