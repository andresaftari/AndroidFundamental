package com.andresaftari.apiexample.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresaftari.apiexample.data.ApiStatus
import com.andresaftari.apiexample.data.remote.api.ApiConfig
import com.andresaftari.apiexample.data.remote.api.model.UserResponse
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val _details = MutableLiveData<UserResponse>()
    val detail: LiveData<UserResponse>
        get() = _details

    private val _status = MutableLiveData<ApiStatus>()
    private val apiService = ApiConfig().service

    private suspend fun requestDetailList(username: String) = try {
        _status.postValue(ApiStatus.LOADING)
        _details.postValue(apiService.getUserDetail(username))
        _status.postValue(ApiStatus.SUCCESS)
    } catch (ex: Exception) {
        Log.d("Details", ex.localizedMessage!!)
        _status.postValue(ApiStatus.FAILED)
    }

    fun getDetail(username: String) = viewModelScope.launch { requestDetailList(username) }
}