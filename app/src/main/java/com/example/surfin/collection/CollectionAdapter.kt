package com.example.surfin.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.surfin.data.Spots
import com.example.surfin.databinding.ItemCollectionBinding
import com.example.surfin.databinding.ItemUserHistoryBinding
import com.example.surfin.history.HistoryAdapter

class CollectionAdapter :
    ListAdapter<Spots, CollectionAdapter.CollectionViewHolder>(DiffCallback) {

    class CollectionViewHolder(private var binding: ItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(spots: Spots) {
            spots.let {
                binding.viewModel = it
                binding.executePendingBindings()
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Spots>() {
        override fun areItemsTheSame(
            oldItem: Spots, newItem: Spots
        ): Boolean {
            return oldItem.long == newItem.long && oldItem.lat == newItem.lat
        }

        override fun areContentsTheSame(
            oldItem: Spots, newItem: Spots
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CollectionViewHolder {
        return CollectionViewHolder(
            ItemCollectionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


