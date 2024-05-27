package com.amin.oqatsharee.api

import com.amin.oqatsharee.models.AzanResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("owghat/")
    suspend fun getAzan(@Query("token") token : String , @Query("city") city : String  ) : Response<AzanResponseModel>
}