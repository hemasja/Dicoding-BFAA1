package com.example.dicoding_bfaa1.data.retrofit

import com.example.dicoding_bfaa1.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.*
interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
        fun getGithubUsers(
            @Query("q") user: String
    ): Call<GithubResponse>
}