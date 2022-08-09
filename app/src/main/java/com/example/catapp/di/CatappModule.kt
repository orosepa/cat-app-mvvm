package com.example.catapp.di

import com.example.catapp.data.remote.CatApi
import com.example.catapp.data.repository.CatappRepositoryImpl
import com.example.catapp.domain.repository.CatappRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CatappModule {

    @Singleton
    @Provides
    fun provideCatApi(): CatApi {
        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDataRepository(api: CatApi): CatappRepository = CatappRepositoryImpl(api)
}