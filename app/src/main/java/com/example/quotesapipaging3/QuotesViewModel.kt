package com.example.quotesapipaging3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.quotesapipaging3.repository.QuotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(private val quotesRepository: QuotesRepository) : ViewModel() {

    val list = quotesRepository.getQuotes().cachedIn(viewModelScope)


}