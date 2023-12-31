package com.example.quotesapipaging3.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.quotesapipaging3.db.QuoteDatabase
import com.example.quotesapipaging3.models.QuoteRemoteKeys
import com.example.quotesapipaging3.models.Result
import com.example.quotesapipaging3.network.QuotesAPI
import kotlin.Exception


@OptIn(ExperimentalPagingApi::class)
class QuoteRemoteMediator(
    private val quotesAPI: QuotesAPI,
    private val quoteDatabase: QuoteDatabase) : RemoteMediator<Int, Result>(){


    val quoteDao = quoteDatabase.quoteDao()
    val quoteRemoteKeysDao = quoteDatabase.remoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {
        //Fetch Quotes from API
        //Save these Quotes + RemoteKey Data into DB
        //Logic for States - REFRESH, PREPEND, APPEND.

        return try{

            val currentPage = when(loadType){
                LoadType.REFRESH ->{
                    val remoteKey = getRemoteKeyClosesToCurrentPosition(state)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND ->{
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }

            }





           // val currentPage = 1
            val response = quotesAPI.getQuotes(currentPage)
            val endOfPaginationReached = response.totalPages == currentPage

            val prevPage = if(currentPage == 1) null else currentPage - 1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

            quoteDatabase.withTransaction {

                if(loadType == LoadType.REFRESH){
                    quoteDao.deleteQuotes()
                    quoteRemoteKeysDao.deleteAllRemoteKeys()
                }

                quoteDao.addQuotes(response.results)
                val keys = response.results.map { quote ->
                    QuoteRemoteKeys(
                        id = quote._id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                quoteRemoteKeysDao.addAllRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception){
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosesToCurrentPosition(state: PagingState<Int, Result>) :
            QuoteRemoteKeys?{
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?._id?.let {id ->
                quoteRemoteKeysDao.getRemoteKeys(id=id)

            }
        }
    }


    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Result>) : QuoteRemoteKeys?{
        return state.pages.firstOrNull(){it.data.isNotEmpty() } ?.data?.firstOrNull()?.let { quote ->
            quoteRemoteKeysDao.getRemoteKeys(quote._id)

        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Result>) : QuoteRemoteKeys? {
        return state.pages.lastOrNull() {it.data.isNotEmpty()} ?.data?.lastOrNull()
            ?.let { quote ->
                quoteRemoteKeysDao.getRemoteKeys(id = quote._id)
            }
    }
}