package com.app.network.networkModule.interfaceModule

import com.app.network.networkModule.models.LoginUIDetails
import retrofit2.Response
import retrofit2.http.GET

internal interface ApiInterface {

    @GET("/mobileapps/android_assignment.json")
    suspend fun getLoginScreenInfo(): Response<LoginUIDetails>
}