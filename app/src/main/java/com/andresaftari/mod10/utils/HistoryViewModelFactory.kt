package com.andresaftari.mod10.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andresaftari.mod10.db.BmiDao
import com.andresaftari.mod10.ui.history.HistoryViewModel

@Suppress("UNCHECKED_CAST")
class HistoryViewModelFactory(
    private val dao: BmiDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) return HistoryViewModel(dao) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}