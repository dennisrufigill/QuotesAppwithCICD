package com.example.quotesapipaging3.models

data class QuotesApiResponse(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<Result>,
    val totalCount: Int,
    val totalPages: Int
)