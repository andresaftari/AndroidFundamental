package com.andresaftari.apiexample.data.remote.api

import com.andresaftari.apiexample.data.remote.api.model.Item
import com.andresaftari.apiexample.data.remote.api.model.SearchResponse
import com.andresaftari.apiexample.data.remote.api.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoint {
    @GET(ENDPOINT_SEARCH)
    suspend fun getUserSearch(@Query("q") q: String): SearchResponse

    @GET(ENDPOINT_USERNAME)
    suspend fun getUserDetail(@Path("username") username: String): UserResponse

    @GET(ENDPOINT_FOLLOWERS)
    suspend fun getFollowerList(@Path("username") username: String): ArrayList<Item>

    @GET(ENDPOINT_FOLLOWING)
    suspend fun getFollowingList(@Path("username") username: String): ArrayList<Item>

    @GET(ENDPOINT_USER)
    suspend fun getUserList(): ArrayList<UserResponse>
}