package com.example.locationcontrol.data

import com.example.locationcontrol.R
import com.example.locationcontrol.data.model.Location
import com.example.locationcontrol.data.model.Place
import com.example.locationcontrol.data.model.Result
import java.lang.Exception

class LocationsRepository(private val dataSource: LocationsDataSource) {
    fun loadLocations(place : Int) : List<Location>{
        return when(place){
            R.id.wiata_rb -> Place.Wiata().list
            R.id.bexy_rb -> Place.Bexy().list
            R.id.w_rb -> Place.W().list
            R.id.d_rb -> Place.D().list
            R.id.s_rb -> Place.S().list
            else -> throw Exception("unknown place type")
        }

    }

    fun send(locationList : List<Location>) : Result<Boolean>{
        return dataSource.send(locationList)
    }

    fun checkCompatibility(location : Location) : Result<Boolean>{
        return dataSource.checkLocation(location)
    }
}