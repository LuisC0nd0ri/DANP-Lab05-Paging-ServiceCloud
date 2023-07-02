package com.luiscv.mylab05.paging

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.luiscv.mylab05.entities.SensorDataItem
import com.luiscv.mylab05.model.MyRepository
import com.luiscv.mylab05.model.SensorDataItemDao
import kotlinx.coroutines.flow.Flow

class MyViewModel(
    private val myDao: SensorDataItemDao //estos parametros son del constructor
) : ViewModel() {

    private val repository = MyRepository(myDao)

    fun getData(): Flow<PagingData<SensorDataItem>> {
        return repository.getData()
    }
}