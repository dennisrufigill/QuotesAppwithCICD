package com.example.quotesapipaging3.network

import com.example.quotesapipaging3.models.QuotesApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QuotesAPI {

    @GET("quotes")
    suspend fun getQuotes(@Query("page") page : Int) : QuotesApiResponse


}