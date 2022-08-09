package com.example.catapp.di

import com.example.catapp.data.remote.CatApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CatappModule {

    @Singleton
    @Provides
    fun provideCatApi(): CatApi {
        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com")
            .build()
            .create(CatApi::class.java)
    }
}