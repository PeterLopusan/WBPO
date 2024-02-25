package com.example.wbpoapp.api

import com.example.wbpoapp.models.RegistrationModel
import com.example.wbpoapp.models.UserListModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @POST(REGISTER_URL)
    suspend fun registerUser(@Body body: RegistrationModel): Response<Void>

    @GET(USER_LIST_URL)
    suspend fun getUserList(@Query("page") page: Int): Response<UserListModel>
}