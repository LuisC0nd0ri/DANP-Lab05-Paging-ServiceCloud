package com.luiscv.mylab05.paging

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.luiscv.mylab05.entities.SensorRegister
import kotlinx.coroutines.flow.Flow

class MyViewModel_Rest() : ViewModel() {

    fun getData(): Flow<PagingData<SensorRegister>> {
        return Pager(config = PagingConfig(pageSize = 10)) {
            MyPagingSource_rest()
        }.flow
    }
}