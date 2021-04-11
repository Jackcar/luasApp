package com.jacksonueda.test.ui.repo.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jacksonueda.test.data.model.Issue
import com.jacksonueda.test.databinding.ItemRepoIssueBinding

class RepoIssuesAdapter :
    PagingDataAdapter<Issue, RepoIssuesAdapter.RepoViewHolder>(RepoIssueComparator) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoIssuesAdapter.RepoViewHolder =
        RepoViewHolder(
            ItemRepoIssueBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RepoIssuesAdapter.RepoViewHolder, position: Int) {
        holder.binding.apply {
            issue = getItem(position)
            executePendingBindings()
        }
    }

    inner class RepoViewHolder(val binding: ItemRepoIssueBinding) :
        RecyclerView.ViewHolder(binding.root)

    object RepoIssueComparator : DiffUtil.ItemCallback<Issue>() {
        override fun areItemsTheSame(oldItem: Issue, newItem: Issue) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Issue, newItem: Issue) =
            oldItem == newItem
    }

}