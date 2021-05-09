package com.andresaftari.apiexample.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "ISI AJA SAMA ABOUT ATAU APALAH, INI TAMBAHAN AJA"
    }
    val text: LiveData<String> = _text
}