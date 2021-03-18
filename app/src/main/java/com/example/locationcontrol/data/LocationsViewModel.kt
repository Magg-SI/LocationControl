package com.example.locationcontrol.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locationcontrol.data.model.Location
import com.example.locationcontrol.data.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationsViewModel(private val locationsRepository : LocationsRepository) : ViewModel() {
    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> = _locations

    private val _sendingResult = MutableLiveData<Boolean>()
    val sendingResult: LiveData<Boolean> = _sendingResult

    private val _compatibility = MutableLiveData<Boolean>()
    val compatibility: LiveData<Boolean> = _compatibility

    private val _sendingState = MutableLiveData<Boolean>()
    val sendingState: LiveData<Boolean> = _sendingState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    fun loadLocations(place : Int){
        _locations.postValue(locationsRepository.loadLocations(place))
    }

    fun send(locationList : List<Location>){
        viewModelScope.launch(Dispatchers.IO){
            val result = locationsRepository.send(locationList)
            if (result is Result.Success){
                _sendingResult.postValue(true)
            }else if (result is Result.Error){
                _errorMessage.postValue(result.message)
            }else if(result is Result.ConnectionError){
                _errorMessage.postValue("Błąd połączenia z internetem")
            }
        }
    }

    fun checkCompatibility(location : Location){
        viewModelScope.launch(Dispatchers.IO) {
            val result = locationsRepository.checkCompatibility(location)
            location.sendingState = false
            if (result is Result.Success){
                location.compatible = result.data
                _compatibility.postValue(result.data)
            }else if (result is Result.Error){
                _errorMessage.postValue(result.message)
            }else if(result is Result.ConnectionError){
                _errorMessage.postValue("Błąd połączenia z internetem")
            }
        }
    }
}