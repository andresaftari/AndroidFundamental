package com.andresaftari.apiexample.ui.follows

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresaftari.apiexample.data.ApiStatus
import com.andresaftari.apiexample.data.remote.api.ApiConfig
import com.andresaftari.apiexample.data.remote.api.model.Item
import kotlinx.coroutines.launch

class FollowViewModel : ViewModel() {
    private val _followers = MutableLiveData<ArrayList<Item>>()
    val follower: LiveData<ArrayList<Item>>
        get() = _followers

    private val _following = MutableLiveData<ArrayList<Item>>()
    val following: LiveData<ArrayList<Item>>
        get() = _following

    private val _status = MutableLiveData<ApiStatus>()
    private val apiService = ApiConfig().service

    private suspend fun requestFollowerList(username: String) = try {
        _status.postValue(ApiStatus.LOADING)
        _followers.postValue(apiService.getFollowerList(username))
        _status.postValue(ApiStatus.SUCCESS)
    } catch (ex: Exception) {
        Log.d("FollowerList", ex.localizedMessage!!)
        _status.postValue(ApiStatus.FAILED)
    }

    private suspend fun requestFollowingList(username: String) = try {
        _status.postValue(ApiStatus.LOADING)
        _following.postValue(apiService.getFollowingList(username))
        _status.postValue(ApiStatus.SUCCESS)
    } catch (ex: Exception) {
        Log.d("FollowingList", ex.localizedMessage!!)
        _status.postValue(ApiStatus.FAILED)
    }

    fun getFollowers(username: String) = viewModelScope.launch { requestFollowerList(username) }
    fun getFollowings(username: String) = viewModelScope.launch { requestFollowingList(username) }
}