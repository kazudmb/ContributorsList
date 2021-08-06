package com.dmb.contributorslist.viewModel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.dmb.contributorslist.service.model.User
import com.dmb.contributorslist.service.repository.ContributorsRepository
import kotlinx.coroutines.launch

class UserViewModel(
    private val myApplication: Application,
    private val mLoginName: String
) : AndroidViewModel(myApplication) {

    private val repository = ContributorsRepository.instance
    val userLiveData: MutableLiveData<User> = MutableLiveData()

    var user = ObservableField<User>()

    init {
        loadUser()
    }

    private fun loadUser() = viewModelScope.launch { //onCleared() のタイミングでキャンセルされる
        try {
            val response = repository.getUser(mLoginName)
            if (response.isSuccessful) {
                userLiveData.postValue(response.body())
            }
        } catch (e: Exception) {
            Log.e("loadUser:Failed", e.stackTrace.toString())
        }
    }


    fun setUser(user: User) {
        this.user.set(user)
    }

    class Factory(private val application: Application, private val loginName: String) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserViewModel(application, loginName) as T
        }
    }
}