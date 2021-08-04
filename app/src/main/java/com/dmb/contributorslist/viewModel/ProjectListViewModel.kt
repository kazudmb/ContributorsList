package com.dmb.contributorslist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dmb.contributorslist.R
import com.dmb.contributorslist.service.model.Project
import com.dmb.contributorslist.service.repository.ProjectRepository
import kotlinx.coroutines.launch

class ProjectListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProjectRepository.instance
    var projectListLiveData: MutableLiveData<List<Project>> = MutableLiveData()

    init {
        loadProjectList()
    }

    private fun loadProjectList() = viewModelScope.launch { //onCleared() のタイミングでキャンセルされる
        try {
            val response = repository.getProjectList(getApplication<Application>().getString(R.string.github_user_name))
            if (response.isSuccessful) {
                projectListLiveData.postValue(response.body()) //データを取得したら、LiveDataを更新
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}