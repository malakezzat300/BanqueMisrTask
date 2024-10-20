package com.malakezzat.banquemisr.challenge05.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3NjZhMGJkOWQyNzBhNzA5MDNlZGE2MjZmZjcyZmNjZCIsIm5iZiI6MTcyOTI5MTEyNy4xNTM1MDcsInN1YiI6IjY3MTJlMjE3OGU4NDQ2NTdiN2ZiNTNlMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.nICKp4KGR4SQ1Bs1oI99hawE8bIPnQY2GQcMugYBuAg")
                .build()
            chain.proceed(request)
        }
        .build()

    private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}

