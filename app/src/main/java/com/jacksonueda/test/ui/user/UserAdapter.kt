package com.jacksonueda.test.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jacksonueda.test.data.model.User
import com.jacksonueda.test.databinding.ItemUserBinding

class UserAdapter constructor(
    private var clickListener: UserClickListener
) :
    PagingDataAdapter<User, UserAdapter.UserViewHolder>(UserComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.apply {
            user = getItem(position)
            executePendingBindings()
        }
    }

    inner class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                clickListener.onUserClicked(
                    binding,
                    getItem(absoluteAdapterPosition) as User
                )
            }
        }
    }

    object UserComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User) =
            oldItem == newItem
    }

    interface UserClickListener {
        fun onUserClicked(binding: ItemUserBinding, user: User)
    }

}