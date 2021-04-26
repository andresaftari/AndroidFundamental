package com.andresaftari.mod10.ui.history

import androidx.lifecycle.ViewModel
import com.andresaftari.mod10.db.BmiDao

class HistoryViewModel(dao: BmiDao) : ViewModel() {
    val data = dao.getLastBmi()
}