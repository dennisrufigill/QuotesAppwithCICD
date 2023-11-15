package com.example.quotesapipaging3.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.quotesapipaging3.db.QuoteDatabase
import com.example.quotesapipaging3.network.QuotesAPI
import com.example.quotesapipaging3.paging.QuoteRemoteMediator
import javax.inject.Inject

class QuotesRepository @Inject constructor(
    val quotesAPI: QuotesAPI,
    private val quoteDatabase: QuoteDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getQuotes() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        remoteMediator = QuoteRemoteMediator(quotesAPI,quoteDatabase),
        pagingSourceFactory = { quoteDatabase.quoteDao().getQuotes() }
    ).liveData

}