package com.tasks.domain.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tasks.domain.room.Recipe
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://text-host.ru/"

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Recipe::class.java, RecipeDeserializer())
        .create()

    val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        retrofit.create(ApiService::class.java)
    }
}
