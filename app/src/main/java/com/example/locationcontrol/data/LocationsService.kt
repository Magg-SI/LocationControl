package com.example.locationcontrol.data

import com.example.locationcontrol.data.model.APIRequest
import com.example.locationcontrol.data.model.APIResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LocationsService {
    @POST("json.aspx")
    fun checkLocation(@Body request: APIRequest.CheckLocationRequest): Call<APIResponse.Location>

    @POST("json.aspx")
    fun send(@Body request: APIRequest.SendRequest): Call<APIResponse>
}