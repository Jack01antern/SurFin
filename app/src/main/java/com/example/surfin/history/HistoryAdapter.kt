package com.example.surfin.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.surfin.data.Spots
import com.example.surfin.data.UserActivityHistory
import com.example.surfin.databinding.ItemUserHistoryBinding

class HistoryAdapter(private val onClickListener: OnClickListener)  :
    ListAdapter<UserActivityHistory, HistoryAdapter.UserActivityHistoryViewHolder>(DiffCallback) {

    class UserActivityHistoryViewHolder(private var binding: ItemUserHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userActivityHistory: UserActivityHistory,onClickListener: OnClickListener) {
            userActivityHistory.let {
                binding.viewModel = it
                binding.btnEdit.setOnClickListener {
                    onClickListener.onClick(userActivityHistory)
                }
                binding.executePendingBindings()
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<UserActivityHistory>() {
        override fun areItemsTheSame(
            oldItem: UserActivityHistory, newItem: UserActivityHistory
        ): Boolean {
            return oldItem.activityId == newItem.activityId
        }

        override fun areContentsTheSame(
            oldItem: UserActivityHistory, newItem: UserActivityHistory
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): UserActivityHistoryViewHolder {
        return UserActivityHistoryViewHolder(
            ItemUserHistoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: UserActivityHistoryViewHolder, position: Int) {
        holder.bind(getItem(position),onClickListener)
    }

    class OnClickListener(val clickListener: (userActivityHistory: UserActivityHistory) -> Unit) {
        fun onClick(userActivityHistory: UserActivityHistory) = clickListener(userActivityHistory)
    }
}


