package com.jacksonueda.test.ui.repo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jacksonueda.test.R
import com.jacksonueda.test.databinding.ItemRepoLoadStateFooterBinding

class ReposLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ReposLoadStateAdapter.RepoLoadStateViewHolder>() {

    override fun onBindViewHolder(
        holder: RepoLoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RepoLoadStateViewHolder {
        return RepoLoadStateViewHolder.create(parent, retry)
    }

    class RepoLoadStateViewHolder(
        val binding: ItemRepoLoadStateFooterBinding,
        private val retry: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMessage.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMessage.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): RepoLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_repo_load_state_footer, parent, false)
                val binding = ItemRepoLoadStateFooterBinding.bind(view)
                return RepoLoadStateViewHolder(binding, retry)
            }
        }
    }
}