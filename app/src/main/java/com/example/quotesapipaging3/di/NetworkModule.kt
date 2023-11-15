package com.example.quotesapipaging3.di

import com.example.quotesapipaging3.network.QuotesAPI
import com.example.quotesapipaging3.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getQuoteAPI(retrofit: Retrofit) : QuotesAPI {
        return retrofit.create(QuotesAPI::class.java)
    }
}