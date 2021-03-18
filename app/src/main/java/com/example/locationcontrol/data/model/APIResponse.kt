package com.example.locationcontrol.data.model

import com.google.gson.annotations.SerializedName

open class APIResponse(val errCode : Int, @SerializedName("errOpis") val message : String) {
    companion object{
        const val OK = 0
        const val NOT_OK = 1
    }
    class Login(retCode: Int, message: String):
        APIResponse(retCode, message){
        val token : String? = null
        @SerializedName("userID")
        val id : Int? = null
    }
    class Location(retCode :Int, message : String,
                   @SerializedName("nazwa") var name : String,
                   @SerializedName("lokalizacja") var localization : String,
                   @SerializedName("perfectLoc") var perfLocalization :  String?,
                   @SerializedName("nrKolczyka") var tag : String?,
                   @SerializedName("nrBB") var nrBB : Int?): APIResponse(retCode,message)

}