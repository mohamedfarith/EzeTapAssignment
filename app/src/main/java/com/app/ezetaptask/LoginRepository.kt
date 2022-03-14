package com.app.ezetaptask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.app.network.networkModule.models.LoginUIDetails
import com.app.network.networkModule.models.NetworkResult

class LoginRepository(
    private val loginViewModel: LoginViewModel
) {

    companion object {
        fun getInstance(owner: ViewModelStoreOwner): LoginRepository {
            val repository by lazy {
                LoginRepository(ViewModelProvider(owner)[LoginViewModel::class.java])
            }
            return repository;
        }
    }


    fun getLoginScreenDetails(): MutableLiveData<NetworkResult<LoginUIDetails>> {
        return loginViewModel.getLoginDetailsFromServer()
    }
}