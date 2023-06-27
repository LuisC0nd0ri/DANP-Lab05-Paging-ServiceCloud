package com.luiscv.mylab05.operations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun addSensorDataItem(
    showDialogDataRegister: MutableState<Boolean>
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
                    showDialogDataRegister.value = false
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