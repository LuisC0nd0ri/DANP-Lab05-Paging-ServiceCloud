package com.luiscv.mylab05.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luiscv.mylab05.entities.SensorDataItem
import com.luiscv.mylab05.model.SensorDataItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyPagingSource(
    //para hacer las consultas, private val para que se asigne como propiedad de clase
    private val myDao: SensorDataItemDao
    ) : PagingSource<Int, SensorDataItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SensorDataItem> {
        val pageSize = 10

        val currentPage = params.key ?: 1
        val nextPage = currentPage + 1

        val startIndex = (currentPage * pageSize) - 10
        val endIndex = (currentPage * pageSize) - 1

        val tamanioData = withContext(Dispatchers.IO) {
            myDao.getTotalItemCount()
        }
        Log.d("NUM DATOS", tamanioData.toString())

        val currentPageData = withContext(Dispatchers.IO) {
            if (startIndex+1 <= tamanioData) {
                if (endIndex+1 <= tamanioData) {
                    myDao.getItemsByPage(pageSize, startIndex)
                } else {
                    myDao.getItemsByPage(tamanioData % pageSize, startIndex)
                }

            } else {
                emptyList()
            }
        }

        if(currentPageData.isNotEmpty()){
            Log.d("Numero de pagina", currentPage.toString())
        }

        return LoadResult.Page(
            data = currentPageData,
            prevKey = null,
            nextKey = if (currentPageData.isNotEmpty()) nextPage else null
        )
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
