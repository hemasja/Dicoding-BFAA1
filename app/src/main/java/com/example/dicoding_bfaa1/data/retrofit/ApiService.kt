package com.example.dicoding_bfaa1.data.retrofit

import com.example.dicoding_bfaa1.BuildConfig
import com.example.dicoding_bfaa1.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.*
interface ApiService {
    @Headers("Authorization: token " + BuildConfig.API_KEY)
    @GET("search/users")
        fun getGithubUsers(
            @Query("q") user: String
    ): Call<GithubResponse>
}