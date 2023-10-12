package com.example.surfin.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.surfin.databinding.ItemDetailBinding

class DetailAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<String, DetailAdapter.DetailViewHolder>(DiffCallback) {

    class DetailViewHolder(private var binding: ItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(spots: String,onClickListener: OnClickListener) {
            binding.url = spots
            Log.i("detail spots", "$spots" )
            binding.root.setOnClickListener {
                onClickListener.onClick(spots)
            }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String, newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String, newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DetailViewHolder {
        return DetailViewHolder(
            ItemDetailBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(getItem(position),onClickListener)
    }

    class OnClickListener(val clickListener: (spots:String) -> Unit) {
        fun onClick(spots:String) = clickListener(spots)
    }
}


