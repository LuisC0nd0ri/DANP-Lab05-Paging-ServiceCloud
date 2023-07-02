package com.luiscv.mylab05.paging

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.luiscv.mylab05.operations.RestApi
import com.luiscv.mylab05.operations.SensorRegister
import kotlinx.coroutines.flow.Flow

class MyViewModel_Rest() : ViewModel() {

    fun getData(): Flow<PagingData<SensorRegister>> {
        return Pager(config = PagingConfig(pageSize = 10)) {
            MyPagingSource_rest()
        }.flow
    }
}