package com.andresaftari.apiexample.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresaftari.apiexample.data.ApiStatus
import com.andresaftari.apiexample.data.remote.api.ApiConfig
import com.andresaftari.apiexample.data.remote.api.model.SearchResponse
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _userSearch = MutableLiveData<SearchResponse>()
    val userSearch: LiveData<SearchResponse>
        get() = _userSearch

    private val _status = MutableLiveData<ApiStatus>()
    private val apiService = ApiConfig().service

    private suspend fun requestSearch(q: String) = try {
        _status.postValue(ApiStatus.LOADING)
        _userSearch.postValue(apiService.getUserSearch(q))
        _status.postValue(ApiStatus.SUCCESS)
    } catch (ex: Exception) {
        Log.d("SearchList", ex.localizedMessage!!)
        _status.postValue(ApiStatus.FAILED)
    }

    fun getSearchData(q: String) = viewModelScope.launch { requestSearch(q) }
}