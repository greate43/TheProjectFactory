package com.sk.greate43.theprojectfactory.service.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Countries : Parcelable {
    @SerializedName("name")
    var name: String? = ""

    @SerializedName("code")
    var code:String= ""

    var isSelected = false
}