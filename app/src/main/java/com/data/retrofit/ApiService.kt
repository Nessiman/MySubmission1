package com.data.retrofit

import com.data.response.DetailUserResponse
import com.data.response.FollowersAndFollowingItem
import com.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("search/users")
    fun getGithubUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>


    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String
    ):Call<List<FollowersAndFollowingItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String
    ): Call<List<FollowersAndFollowingItem>>
}