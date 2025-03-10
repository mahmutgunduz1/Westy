package com.mahmutgunduz.westy.di

import com.mahmutgunduz.westy.data.ShoppingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Ağ istekleri için gerekli bağımlılıkları sağlayan Dagger-Hilt modülü
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideShoppingApi(retrofit: Retrofit): ShoppingApi {
        return retrofit.create(ShoppingApi::class.java)
    }
} 