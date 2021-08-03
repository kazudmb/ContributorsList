package com.dmb.contributorslist

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val HTTPS_API_GITHUB_URL = "https://api.github.com/"

class ProjectRepository {

    companion object Factory {
        val instance: ProjectRepository
            @Synchronized get() {
                return ProjectRepository()
            }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(HTTPS_API_GITHUB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val contributorsListService: ContributorsListService = retrofit.create(ContributorsListService::class.java)

    suspend fun getProjectList(userId: String): Response<List<Project>> =
        contributorsListService.getProjectList(userId)

    suspend fun getProjectDetails(userID: String, projectName: String): Response<Project> =
        contributorsListService.getProjectDetails(userID, projectName)
}