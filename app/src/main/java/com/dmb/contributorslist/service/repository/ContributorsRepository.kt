package com.dmb.contributorslist.service.repository

import com.dmb.contributorslist.service.model.Contributors
import com.dmb.contributorslist.service.model.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val HTTPS_API_GITHUB_URL = "https://api.github.com/"

class ContributorsRepository {

    companion object Factory {
        val instance: ContributorsRepository
            @Synchronized get() {
                return ContributorsRepository()
            }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(HTTPS_API_GITHUB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val githubService: GithubService = retrofit.create(
        GithubService::class.java)

    suspend fun getContributorsList(): Response<List<Contributors>> =
        githubService.getContributorsList()

    suspend fun getUser(loginName: String): Response<User> =
        githubService.getUser(loginName)
}