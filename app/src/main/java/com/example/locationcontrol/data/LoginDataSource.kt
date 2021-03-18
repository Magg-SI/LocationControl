package com.example.locationcontrol.data
import com.example.locationcontrol.data.model.APIRequest
import com.example.locationcontrol.data.model.APIResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import com.example.locationcontrol.data.model.Result
import com.example.locationcontrol.data.model.User

class LoginDataSource {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://mp.magg.pl/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun login(
        login: String,
        password: String
    ): Result<User> {
        try {
            val service = retrofit.create(LoginService::class.java)
            val result = service
                .login(APIRequest.Login(login, password))
                .execute()

            return when (result.body()!!.errCode) {
                APIResponse.OK -> {
                    Result.Success(User(login,result.body()!!.token!!,result.body()!!.id!!))
                }
                else -> {
                    Result.Error(result.body()!!.message)
                }
            }

        } catch (e: IOException) {
            return Result.ConnectionError()
        }
    }
}