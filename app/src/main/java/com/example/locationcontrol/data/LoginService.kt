package com.example.locationcontrol.data

import com.example.locationcontrol.data.model.APIRequest
import com.example.locationcontrol.data.model.APIResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("json.aspx")
    fun login(@Body request: APIRequest.Login): Call<APIResponse.Login>
}