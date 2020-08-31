package com.sk.greate43.theprojectfactory.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sk.greate43.theprojectfactory.service.model.Country
import com.sk.greate43.theprojectfactory.service.repository.CountriesRepository


class CountriesViewModel(application: Application) : AndroidViewModel(application) {

    fun getCountries(): LiveData<ArrayList<Country>> {
        return CountriesRepository.getCountries()
    }

}