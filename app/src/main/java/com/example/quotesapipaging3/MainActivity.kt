package com.example.quotesapipaging3

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.quotesapipaging3.databinding.ActivityMainBinding
import com.example.quotesapipaging3.paging.LoaderAdapter
import com.example.quotesapipaging3.paging.QuotesPagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var quotesPagingAdapter: QuotesPagingAdapter
    private val quotesViewModel : QuotesViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        callApiAndSetDataOnViews()
    }

    private fun callApiAndSetDataOnViews(){
        quotesPagingAdapter = QuotesPagingAdapter()
        binding.apply {
            quotesRecyclerView.apply {
                setHasFixedSize(true)
                adapter = quotesPagingAdapter.withLoadStateHeaderAndFooter(
                    header = LoaderAdapter(),
                    footer = LoaderAdapter()
                )
            }
        }

        quotesViewModel.list.observe(this) {
            if(it != null){
                quotesPagingAdapter.submitData(lifecycle, it)
            }

        }
    }
}