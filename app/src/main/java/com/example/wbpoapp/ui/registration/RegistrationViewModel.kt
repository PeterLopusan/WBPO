package com.example.wbpoapp.ui.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbpoapp.api.RetrofitObject
import com.example.wbpoapp.models.RegistrationModel
import kotlinx.coroutines.launch

class RegistrationViewModel: ViewModel() {

    fun registerUser(body: RegistrationModel): MutableLiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()

        viewModelScope.launch {
            try {
                val response= RetrofitObject.apiInterface.registerUser(body)
                liveData.value = response.isSuccessful
            } catch (e: Exception) {
                liveData.value = false
            }
        }

        return liveData
    }
}