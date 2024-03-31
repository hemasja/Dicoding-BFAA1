package com.example.dicoding_bfaa1.data.retrofit


import com.example.dicoding_bfaa1.data.response.GithubResponse
import com.example.dicoding_bfaa1.data.response.ItemsItem
import com.example.dicoding_bfaa1.data.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.*
interface ApiService {
    @GET("search/users")
        fun getGithubUsers(
            @Query("q") user: String
    ): Call<GithubResponse>

    @GET("users/{username}")
        fun getUserDetail(@Path("username") username: String):Call<UserDetailResponse>

    @GET("users/{username}/followers")
        fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
        fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}