package com.sk.greate43.theprojectfactory.service.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.sk.greate43.theprojectfactory.service.model.Country
import io.reactivex.rxjava3.schedulers.Schedulers


object CountriesRepository {
    val TAG = CountriesRepository::class.java.simpleName
    fun getCountries(): LiveData<ArrayList<Country>> {
        return LiveDataReactiveStreams.fromPublisher(ServiceGenerator.getRequestApi()
            .listCountries()
            .subscribeOn(Schedulers.io()))
    }
}