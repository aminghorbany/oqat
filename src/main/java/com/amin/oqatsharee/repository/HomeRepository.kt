package com.amin.oqatsharee.repository

import com.amin.oqatsharee.api.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api : ApiServices)  {
    suspend fun getAzan(token : String , city : String) = api.getAzan(token, city)
}