package com.luiscv.mylab05

//todo: ESTE ES UN EJEMPLO DEL USO DE JETPACK COMPOSE Y PAGING
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.room.Room
import com.luiscv.mylab05.entities.SensorDataItem
import com.luiscv.mylab05.model.AppDatabase
import com.luiscv.mylab05.mycomponents.SensorDataItemCard
import com.luiscv.mylab05.operations.addSensorDataItem
import com.luiscv.mylab05.paging.MyViewModel
import com.luiscv.mylab05.ui.theme.MyLab05Theme
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {

    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //dialogos
            var showDialogDataRegister: MutableState<Boolean> =
                remember { mutableStateOf(false) }

            MyLab05Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Scaffold(
                        floatingActionButton = {
                            FloatingButton {
                                // Acción a realizar cuando se hace clic en el botón flotante
                                Toast.makeText(this@MainActivity, "Botón flotante clicado", Toast.LENGTH_SHORT).show()
                                showDialogDataRegister.value = true
                            }
                        }
                    ) {
                        // Contenido principal de la actividad
                        MyApp(viewModel, showDialogDataRegister)
                    }


                }
            }
        }
    }
}

@Composable
fun FloatingButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .size(112.dp)
            .padding(32.dp),
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(4.dp)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Agregar")
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MyApp(viewModel: MyViewModel, showDialogDataRegister: MutableState<Boolean>) {

    //todo: ROOM===================================================================

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

    //para que funcione con suspend
    runBlocking {
        withContext(Dispatchers.IO) {
            dao.insertAll(listaSensorData)
            listaNuevaSensorData = dao.getAllSensorData()
        }
    }
    //todo: ROOM===================================================================

    Thread.sleep(3000)

    //Cargar datos
    val dataItems = viewModel.getData().collectAsLazyPagingItems()
    //Log.d("confirmar carga de datos", dataItems.get(1).toString())

    Column() {
        Text(
            text = "Registros de temperatura",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(20.dp)
        )

        LazyColumn {
            items(dataItems) { dataItem ->
                dataItem?.let { item ->
                    DataItemRow(dataItem = item)
                }
            }
            dataItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val errorMessage = (loadState.refresh as LoadState.Error).error.message
                        item { ErrorItem(errorMessage = errorMessage) }
                    }
                    loadState.append is LoadState.Error -> {
                        val errorMessage = (loadState.append as LoadState.Error).error.message
                        item { ErrorItem(errorMessage = errorMessage) }
                    }
                }
            }
        }
    }

    addSensorDataItem(showDialogDataRegister)

}

@Composable
fun DataItemRow(dataItem: SensorDataItem) {
    // Aquí puedes definir el diseño de una fila de item de datos
    SensorDataItemCard(sensorDataItem = dataItem)
}

@Composable
fun LoadingItem() {
    // Aquí puedes definir el diseño de un elemento de carga
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }

}

@Composable
fun ErrorItem(errorMessage: String?) {
    // Aquí puedes definir el diseño de un elemento de error
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorMessage ?: "Error desconocido",
            style = MaterialTheme.typography.body1,
            color = Color.Red
        )
    }

}