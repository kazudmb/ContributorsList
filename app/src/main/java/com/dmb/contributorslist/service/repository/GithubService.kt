package com.dmb.contributorslist.service.repository

import com.dmb.contributorslist.service.model.Contributors
import com.dmb.contributorslist.service.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    //Contributors一覧
    @GET("users/repos/googlesamples/android-architecture-components/contributors")
    suspend fun getContributorsList(): Response<List<Contributors>>

    //User
    @GET("/users/{login}")
    suspend fun getUser(@Path("login") login: String): Response<User>
}