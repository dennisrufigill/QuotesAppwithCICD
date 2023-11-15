package com.example.quotesapipaging3.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quotesapipaging3.models.Result

@Dao
interface QuoteDao {

    @Query("SELECT * from quote")
    fun getQuotes(): PagingSource<Int, Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuotes(quotes: List<Result>)

    @Query("DELETE from quote")
    suspend fun deleteQuotes()

}