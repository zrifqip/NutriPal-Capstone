package com.submission.nutripal.di

import android.content.Context
import com.submission.nutripal.data.UserRepository
import com.submission.nutripal.api.ApiService
import com.submission.nutripal.api.SurveyApiService
import com.submission.nutripal.data.PreferenceManager
import com.submission.nutripal.data.SurveyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthApiService(): ApiService {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://nutripal-auth-api-gsnjwstg4a-et.a.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSurveyApiService(): SurveyApiService {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://nutripal-survey-api-gsnjwstg4a-et.a.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


        return retrofit.create(SurveyApiService::class.java)
    }
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManager(context)
    }
    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideSurveyRepository(apiService: SurveyApiService): SurveyRepository {
        return SurveyRepository(apiService)
    }

}