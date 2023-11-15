package com.example.quotesapipaging3.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quotesapipaging3.models.QuoteRemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("SELECT * from quoteRemoteKeys where id = :id")
    suspend fun getRemoteKeys(id : String) : QuoteRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys : List<QuoteRemoteKeys>)

    @Query("DELETE from QuoteRemoteKeys")
    suspend fun deleteAllRemoteKeys()

}