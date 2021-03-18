package com.example.locationcontrol.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.locationcontrol.R
import com.example.locationcontrol.data.model.Location
import com.example.locationcontrol.data.model.Place
import com.example.locationcontrol.data.model.Result
import com.example.locationcontrol.data.model.User
import java.lang.Exception

class LoginRepository(private val dataSource: LoginDataSource, private val context : Context) {
    companion object{
        const val LOGIN_PREFERENCES = "com.example.locationcontrol"
    }

    var user : User? = null

    init{
        loadUser()
    }

    private fun loadUser(){
        val preferences = context.getSharedPreferences(LOGIN_PREFERENCES,MODE_PRIVATE)
        val login = preferences.getString("login", null)
        val token = preferences.getString("token",null)
        val id = preferences.getInt("id",-1)

        if (login != null && token != null && id != -1)
            user = User(login, token, id)
    }

    fun saveUser(user : User){
        val preferences = context.getSharedPreferences(LOGIN_PREFERENCES,MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("login", user.login)
        editor.putString("token",user.token)
        editor.putInt("id",user.id)
        editor.commit()
    }

    fun logout(){
        val preferences = context.getSharedPreferences(LOGIN_PREFERENCES,MODE_PRIVATE)
        val editor = preferences.edit()
        editor.remove("login")
        editor.remove("token")
        editor.remove("id")
        editor.commit()
    }

    fun login(login : String, password : String) : Result<User> {
        val result =  dataSource.login(login,password)
        if(result is Result.Success){
            saveUser(result.data)
            user = result.data
        }
        return result
    }
}