package com.jacksonueda.test.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jacksonueda.test.data.model.Repo
import com.jacksonueda.test.databinding.ItemRepoBinding

class RepoAdapter constructor(
    private var clickListener: UserClickListener
) :
    PagingDataAdapter<Repo, RepoAdapter.RepoViewHolder>(RepoComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.binding.apply {
            repo = getItem(position)
            executePendingBindings()
        }
    }

    inner class RepoViewHolder(val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                clickListener.onUserClicked(
                    binding,
                    getItem(absoluteAdapterPosition) as Repo
                )
            }
        }
    }

    object RepoComparator : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo) =
            oldItem == newItem
    }

    interface UserClickListener {
        fun onUserClicked(binding: ItemRepoBinding, repo: Repo)
    }

}