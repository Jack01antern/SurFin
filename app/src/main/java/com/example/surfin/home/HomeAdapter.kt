package com.example.surfin.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.surfin.data.Spots
import com.example.surfin.data.UserActivityHistory
import com.example.surfin.databinding.ItemHomeBinding
import com.example.surfin.databinding.ItemUserHistoryBinding

class HomeAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Spots, HomeAdapter.HomeViewHolder>(DiffCallback) {

    class HomeViewHolder(private var binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(spots: Spots, onClickListener: OnClickListener) {
            spots.let {
                binding.viewModel = it
                binding.root.setOnClickListener {
                    onClickListener.onClick(spots)
                }
                binding.executePendingBindings()
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Spots>() {
        override fun areItemsTheSame(
            oldItem: Spots, newItem: Spots
        ): Boolean {
            return oldItem.lat == newItem.lat && oldItem.longitude == newItem.longitude
        }

        override fun areContentsTheSame(
            oldItem: Spots, newItem: Spots
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): HomeViewHolder {
        return HomeViewHolder(
            ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position),onClickListener)
    }

    class OnClickListener(val clickListener: (spots:Spots) -> Unit) {
        fun onClick(spots: Spots) = clickListener(spots)
    }
}


