package com.andresaftari.mod10.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andresaftari.mod10.db.BmiDao
import com.andresaftari.mod10.ui.hitung.HitungViewModel

@Suppress("UNCHECKED_CAST")
class HitungViewModelFactory(
    private val db: BmiDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HitungViewModel::class.java)) return HitungViewModel(db) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}