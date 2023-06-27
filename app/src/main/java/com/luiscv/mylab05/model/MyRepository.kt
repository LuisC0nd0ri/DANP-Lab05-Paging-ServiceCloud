package com.luiscv.mylab05.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.luiscv.mylab05.entities.SensorDataItem
import com.luiscv.mylab05.paging.MyPagingSource
import kotlinx.coroutines.flow.Flow

class MyRepository {
    fun getData(): Flow<PagingData<SensorDataItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { MyPagingSource() }
        ).flow
    }
}