package com.example.locationcontrol.data.model

sealed class Result<out T> {
    class Success<out T>(val data :T) : Result<T>()
    class Error(val message : String): Result<Nothing>()
    class ConnectionError() : Result<Nothing>()
}