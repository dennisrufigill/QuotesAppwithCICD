package com.example.quotesapipaging3.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quotesapipaging3.models.QuoteRemoteKeys
import com.example.quotesapipaging3.models.Result

@Database(entities = [Result::class, QuoteRemoteKeys::class], version = 1)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quoteDao() : QuoteDao
    abstract fun remoteKeysDao() : RemoteKeysDao

}