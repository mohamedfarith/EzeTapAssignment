package com.app.network.networkModule

import com.app.network.networkModule.interfaceModule.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RetrofitClass {

    val apiInterface: ApiInterface by lazy {
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiInterface::class.java)
    }


}