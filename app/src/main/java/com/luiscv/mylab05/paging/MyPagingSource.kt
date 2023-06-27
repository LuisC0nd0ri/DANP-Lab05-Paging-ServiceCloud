package com.luiscv.mylab05.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luiscv.mylab05.entities.SensorDataItem
import com.luiscv.mylab05.listaNuevaSensorData

class MyPagingSource : PagingSource<Int, SensorDataItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SensorDataItem> {
        try {
            val currentPage = params.key ?: 1
            val nextPage = currentPage + 1

            val startIndex = (currentPage * 10) - 10
            val endIndex = (currentPage * 10) - 1

            val data = if (startIndex < listaNuevaSensorData.size) {
                if (endIndex < listaNuevaSensorData.size) {
                    listaNuevaSensorData.subList(startIndex, endIndex + 1)
                } else {
                    listaNuevaSensorData.subList(startIndex, listaNuevaSensorData.size)
                }
            } else {
                null
            }

            Log.d("Numero de pagina", currentPage.toString())

            return LoadResult.Page(
                data = data?: listOf(),
                prevKey = null,
                nextKey = if (data != null) nextPage else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, SensorDataItem>): Int? {
        // Este método se utiliza para obtener la clave de actualización cuando se realiza un "refresh" en la lista.
        // En este ejemplo, simplemente devolvemos la clave de la primera página.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
