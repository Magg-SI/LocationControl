package com.example.locationcontrol.data.model

import com.google.gson.annotations.SerializedName

data class Location(  @SerializedName("lokalizacja") val title : String){
    @SerializedName("nrBB")
    var bb : Int? = null
    @SerializedName("wynik")
    var compatible : Boolean? = null
    var sendingState : Boolean = false
}