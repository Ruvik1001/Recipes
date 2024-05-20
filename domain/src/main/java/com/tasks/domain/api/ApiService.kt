package com.tasks.domain.api

import com.tasks.domain.room.Recipe
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("raw/bez-zagolovka-10608")
    fun getRecipes(): Call<List<Recipe>>
}