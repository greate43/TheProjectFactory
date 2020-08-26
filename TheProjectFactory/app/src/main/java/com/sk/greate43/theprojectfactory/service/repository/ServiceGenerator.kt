package com.sk.greate43.theprojectfactory.service.repository

import android.net.Uri
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


object ServiceGenerator {
    // private val TAG = ServiceGenerator::class.java.simpleName


    private var BASE_URL = "https://api.jsonbin.io/b/"

    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())


    private val retrofit = retrofitBuilder.build()

    private val REQUEST_API: CountriesJsonApi = retrofit.create(CountriesJsonApi::class.java)

    fun getRequestApi(): CountriesJsonApi {
        return REQUEST_API
    }
}