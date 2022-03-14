package com.app.network.networkModule

import com.app.network.networkModule.models.LoginUIDetails
import com.app.network.networkModule.models.NetworkResult
import java.net.HttpURLConnection


object NetworkHelper {

    suspend fun makeLoginScreenFetch(): NetworkResult<LoginUIDetails>? {
        var networkResult: NetworkResult<LoginUIDetails>? = null

        val apiResult = RetrofitClass.apiInterface.getLoginScreenInfo()
        networkResult = if (apiResult.code() == HttpURLConnection.HTTP_OK) {
            NetworkResult.success(apiResult.body())
        } else {
            //handle all other error cases
            NetworkResult.error("Network error", null)
        }
        return networkResult
    }

}
