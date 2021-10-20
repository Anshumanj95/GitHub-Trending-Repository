package com.example.gittrendingapi.api

import com.example.gittrendingapi.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoApi {

    @GET("search/repositories")
    suspend fun getRepository(
        @Query("q")
        searchQuery:String="a"
    ):Response<ApiResponse>
}