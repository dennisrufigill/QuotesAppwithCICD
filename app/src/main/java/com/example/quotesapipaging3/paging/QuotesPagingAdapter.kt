package com.example.quotesapipaging3.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.quotesapipaging3.databinding.QuotesItemBinding
import com.example.quotesapipaging3.models.Result

class QuotesPagingAdapter :
    PagingDataAdapter<Result, QuotesPagingAdapter.QuotesViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val view = QuotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuotesViewHolder((view))
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.binding.apply {
                tvQuotes.text = currentItem?.content
            }
        }
    }

    inner class QuotesViewHolder(val binding: QuotesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }
    }

}


