package com.example.locationcontrol.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locationcontrol.data.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel (private val loginRepository : LoginRepository) : ViewModel(){
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    private val _sendingState = MutableLiveData<Boolean>()
    val sendingState: LiveData<Boolean> = _sendingState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun login(login : String, password : String){
        viewModelScope.launch(Dispatchers.IO){
            val result = loginRepository.login(login,password)
            if (result is Result.Success){
                _loginResult.postValue(true)
            }else if (result is Result.Error){
                _errorMessage.postValue(result.message)
            }else if(result is Result.ConnectionError){
                _errorMessage.postValue("Błąd połączenia z internetem")
            }
        }
    }
}