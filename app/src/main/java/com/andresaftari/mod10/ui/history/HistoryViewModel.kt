package com.andresaftari.mod10.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresaftari.mod10.db.BmiDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(private val dao: BmiDao) : ViewModel() {
    val data = dao.getLastBmi()
    fun hapusData() = viewModelScope.launch { withContext(Dispatchers.IO) { dao.clearData() } }
}