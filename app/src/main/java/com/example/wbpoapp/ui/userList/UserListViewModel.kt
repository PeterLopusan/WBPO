package com.example.wbpoapp.ui.userList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbpoapp.api.RetrofitObject
import com.example.wbpoapp.models.UserModel
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {
    val userList: MutableLiveData<MutableList<UserModel>?> = MutableLiveData<MutableList<UserModel>?>()
    var listEnd = false

    fun getUserList(page: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitObject.apiInterface.getUserList(page)

                if (page == 1) {
                    userList.value = response.body()?.data
                } else {
                    response.body()?.data?.let { userList.value?.addAll(it) }
                    userList.value = userList.value
                }

                listEnd = response.body()?.data?.isEmpty() == true

            } catch (e: Exception) {
                userList.value = null
            }
        }
    }
}