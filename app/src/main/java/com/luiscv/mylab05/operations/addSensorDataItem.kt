package com.luiscv.mylab05.operations

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luiscv.mylab05.MainActivity
import com.luiscv.mylab05.entities.SensorDataItem
import com.luiscv.mylab05.model.SensorDataItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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

    if(showDialogDataRegister.value){
        AlertDialog(
            onDismissRequest = { /*TODO*/ showDialogDataRegister.value = false },
            confirmButton = {
                TextButton(onClick = { /*TODO*/
                    //AQUI AGREGAMOS EL CODIGO PARA REGISTRAR

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