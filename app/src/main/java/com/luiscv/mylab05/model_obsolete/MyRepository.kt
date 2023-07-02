package com.luiscv.mylab05.model_obsolete

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.luiscv.mylab05.entities_obsolete.SensorDataItem
import com.luiscv.mylab05.paging_obsolete.MyPagingSource
import kotlinx.coroutines.flow.Flow

class MyRepository(
    private val myDao: SensorDataItemDao
) {
    fun getData(): Flow<PagingData<SensorDataItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { MyPagingSource(myDao) }
        ).flow
    }
}