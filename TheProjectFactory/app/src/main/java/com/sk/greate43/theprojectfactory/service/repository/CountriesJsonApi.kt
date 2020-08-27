package com.sk.greate43.theprojectfactory.service.repository

import com.sk.greate43.theprojectfactory.service.model.Country
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET


interface CountriesJsonApi {

    @GET("5f45284c4d8ce411138076f3")
    fun listCountries(): Flowable<ArrayList<Country>>
}