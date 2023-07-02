package com.luiscv.mylab05.paging

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luiscv.mylab05.operations.RestApi
import com.luiscv.mylab05.operations.SensorRegister
import com.luiscv.mylab05.operations.SensorRegisterContainer
import com.luiscv.mylab05.operations.getData_Retrofit_all
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

class MyPagingSource_rest() : PagingSource<Int, SensorRegister>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SensorRegister> {
            return try {
                val startregister = params.key ?: 1
                val pageSize = 10
                val sensorDataItems = convertToSensorDataItems(startregister,pageSize)
                val nextPageKey = if (sensorDataItems.isNotEmpty()) startregister + pageSize else null
                LoadResult.Page(
                    data = sensorDataItems,
                    prevKey = null,
                    nextKey = nextPageKey
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, SensorRegister>): Int? {
            return state.anchorPosition
        }
        private suspend fun convertToSensorDataItems(startregister:Int,maxregisters:Int): List<SensorRegister> {
            val SensorReturn = mutableListOf<SensorRegister>()
            var sensorRegisterContainer: SensorRegisterContainer? = null
            withContext(Dispatchers.IO) {
                sensorRegisterContainer = getData_Retrofit_all(startregister,maxregisters)
            }
            sensorRegisterContainer?.registers?.forEach { sensorRegister ->
                SensorReturn.add(SensorRegister(
                    sensorRegister.RegistroId,
                    sensorRegister.FechayHora,
                    sensorRegister.medida,
                    sensorRegister.comentario)
                )
            }
        return SensorReturn.toList()
        }
    }
