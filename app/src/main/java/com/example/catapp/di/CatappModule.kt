package com.example.catapp.di

import android.app.Application
import androidx.room.Room
import com.example.catapp.data.db.LikedCatsDatabase
import com.example.catapp.data.remote.CatApi
import com.example.catapp.data.repository.CatappRepositoryImpl
import com.example.catapp.domain.repository.CatappRepository
import com.example.catapp.util.Constants
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
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLikedCatsDatabase(app: Application): LikedCatsDatabase {
        return LikedCatsDatabase.getDatabase(app.applicationContext) ?:
        Room.databaseBuilder(
            app,
            LikedCatsDatabase::class.java,
            "liked_cats.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDataRepository(api: CatApi, db: LikedCatsDatabase): CatappRepository = CatappRepositoryImpl(api, db)
}