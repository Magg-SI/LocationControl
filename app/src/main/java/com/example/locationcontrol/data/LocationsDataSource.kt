package com.example.locationcontrol.data

import com.example.locationcontrol.data.model.APIRequest
import com.example.locationcontrol.data.model.APIResponse
import com.example.locationcontrol.data.model.Location
import com.example.locationcontrol.data.model.Result
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class LocationsDataSource {
    private val retrofit = Retrofit.Builder()
            .baseUrl("http://mp.magg.pl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun checkLocation(location: Location) : Result<Boolean> {
        try {
            val service = retrofit.create(LocationsService::class.java)
            val result = service
                    .checkLocation(APIRequest.CheckLocationRequest("", location.bb!! ))
                    .execute()

            return if (result.body()!!.errCode<0) {
                Result.Error(result.body()!!.message)
            } else if (result.body()!!.errCode>0) {
                Result.Success(data = false)
            } else {
                if (result.body()!!.localization == location.title) Result.Success(data = true)
                else Result.Success(data = false)
            }
        }
        catch(e:IOException){
            return Result.ConnectionError()
        }
    }

    fun send(listLocation: List<Location>) : Result<Boolean> {
        try {
            val service = retrofit.create(LocationsService::class.java)
            val result = service
                    .send(APIRequest.SendRequest("", listLocation))
                    .execute()

            return when (result.body()!!.errCode) {
                APIResponse.OK -> {
                    Result.Success(data = true)
                }
                else -> {
                    Result.Error(result.body()!!.message)
                }
            }

        }catch(e:IOException){
            return Result.ConnectionError()
        }
    }
}