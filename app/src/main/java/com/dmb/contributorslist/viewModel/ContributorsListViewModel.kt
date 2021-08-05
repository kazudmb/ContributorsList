package com.dmb.contributorslist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dmb.contributorslist.service.model.Contributors
import com.dmb.contributorslist.service.repository.ContributorsRepository
import kotlinx.coroutines.launch

class ContributorsListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ContributorsRepository.instance
    var contributorsListLiveData: MutableLiveData<List<Contributors>> = MutableLiveData()

    init {
        loadContributorsList()
    }

    private fun loadContributorsList() = viewModelScope.launch { //onCleared() のタイミングでキャンセルされる
        try {
            val response = repository.getContributorsList()
            if (response.isSuccessful) {
                contributorsListLiveData.postValue(response.body()) //データを取得したら、LiveDataを更新
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}