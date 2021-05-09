package com.andresaftari.apiexample.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresaftari.apiexample.data.ApiStatus
import com.andresaftari.apiexample.data.remote.api.ApiConfig
import com.andresaftari.apiexample.data.remote.api.model.UserResponse
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _user = MutableLiveData<ArrayList<UserResponse>>()
    val user: LiveData<ArrayList<UserResponse>>
        get() = _user

    private val _status = MutableLiveData<ApiStatus>()
    private val apiService = ApiConfig().service

    private suspend fun requestUserList() = try {
        _status.postValue(ApiStatus.LOADING)
        _user.postValue(apiService.getUserList())
        _status.postValue(ApiStatus.SUCCESS)
    } catch (ex: Exception) {
        Log.d("UserList", ex.localizedMessage!!)
        _status.postValue(ApiStatus.FAILED)
    }

    fun getUserList() = viewModelScope.launch { requestUserList() }
}