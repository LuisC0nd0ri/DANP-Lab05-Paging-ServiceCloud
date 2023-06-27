package com.luiscv.mylab05.paging

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.luiscv.mylab05.entities.SensorDataItem
import com.luiscv.mylab05.model.MyRepository
import kotlinx.coroutines.flow.Flow

class MyViewModel : ViewModel() {
    private val repository = MyRepository()

    fun getData(): Flow<PagingData<SensorDataItem>> {
        return repository.getData()
    }
}