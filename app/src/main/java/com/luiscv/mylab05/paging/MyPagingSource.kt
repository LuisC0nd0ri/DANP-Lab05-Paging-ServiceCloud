package com.luiscv.mylab05.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luiscv.mylab05.entities.SensorDataItem

class MyPagingSource : PagingSource<Int, SensorDataItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SensorDataItem> {
        // Aquí puedes realizar una llamada a la API o acceder a tu fuente de datos para obtener los datos paginados
        // El parámetro "params" contiene la información sobre el número de página solicitado
        // Debes devolver un objeto LoadResult.Page que contenga los datos cargados y la información de paginación

        try {
            val currentPage = params.key ?: 1
            val nextPage = currentPage + 1

            // Simulación de carga de datos
            val data = mutableListOf<SensorDataItem>()
            for (i in 1..20) {
                val item = SensorDataItem(
                    "26/06/2023",
                    "Item ${i + (currentPage - 1) * 20}",
                    "Ninguno")
                data.add(item)
            }

            Log.d("Numero de pagina", currentPage.toString())

            return LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPage
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
