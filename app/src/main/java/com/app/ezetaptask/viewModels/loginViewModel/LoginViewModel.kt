package com.app.ezetaptask.viewModels.loginViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.network.networkModule.NetworkHelper
import com.app.network.networkModule.models.LoginUIDetails
import com.app.network.networkModule.models.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val loginLiveData: MutableLiveData<NetworkResult<LoginUIDetails>> by lazy {
        MutableLiveData()
    }


    fun getLoginDetailsFromServer(): MutableLiveData<NetworkResult<LoginUIDetails>> {
        loadData()
        return loginLiveData
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val networkResult = NetworkHelper.makeLoginScreenFetch()
            loginLiveData.postValue(networkResult)
        }
    }
}