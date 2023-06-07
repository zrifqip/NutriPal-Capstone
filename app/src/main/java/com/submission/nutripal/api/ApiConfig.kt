package com.submission.submissionintermediate.api

import com.submission.nutripal.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig
{
    companion object
    {
        const val BASE_URL = "https://story-api.dicoding.dev/v1/"
        fun getApiService(): Api
        {
            val client = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val loggingInterceptor =
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(loggingInterceptor)
            }
            else { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE) }

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(Api::class.java)
        }
    }
}