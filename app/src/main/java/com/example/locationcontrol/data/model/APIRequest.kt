package com.example.locationcontrol.data.model


sealed class APIRequest(val func : String) {
    class CheckLocationRequest(val token : String, val nrBB : Int): APIRequest("testLokalizacji" )
    class SendRequest(val token: String,val list: List<Location>): APIRequest("sendRapoLokaliz")
    class Login(val login : String, val password : String) : APIRequest("login")
}