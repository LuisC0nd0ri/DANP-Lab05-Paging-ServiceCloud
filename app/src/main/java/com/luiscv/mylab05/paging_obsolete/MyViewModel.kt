package com.luiscv.mylab05.paging_obsolete

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.luiscv.mylab05.entities_obsolete.SensorDataItem
import com.luiscv.mylab05.model_obsolete.MyRepository
import com.luiscv.mylab05.model_obsolete.SensorDataItemDao
import kotlinx.coroutines.flow.Flow

class MyViewModel(
    private val myDao: SensorDataItemDao //estos parametros son del constructor
) : ViewModel() {

    private val repository = MyRepository(myDao)

    fun getData(): Flow<PagingData<SensorDataItem>> {
        return repository.getData()
    }
}