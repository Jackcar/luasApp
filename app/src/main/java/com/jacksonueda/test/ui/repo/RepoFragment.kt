package com.jacksonueda.test.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.findNavController
import androidx.paging.LoadState
import com.jacksonueda.test.R
import com.jacksonueda.test.data.model.Repo
import com.jacksonueda.test.databinding.ItemRepoBinding
import com.jacksonueda.test.databinding.RepoFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RepoFragment : Fragment(), RepoAdapter.RepoClickListener {

    private lateinit var binding: RepoFragmentBinding

    private val userAdapter = RepoAdapter(this)

    // As the new Android Navigation does not hold the fragment state on the back navigation,
    // we hold it in the ViewModel so we can retrieve it later
    private val viewModel: RepoViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RepoFragmentBinding.inflate(inflater).apply {
        binding = this
        lifecycleOwner = this@RepoFragment
    }.root

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupListeners()
        setupObservers()

        if (viewModel.listState != null) {
            binding.repoRecyclerView.layoutManager?.onRestoreInstanceState(viewModel.listState)
            viewModel.listState = null
        } else {
            viewModel.getRepos()
        }
    }

    /**
     * Function responsible to set up the binding variables or views to the Fragment.
     */
    private fun setupAdapter() {
        with(userAdapter) {
            binding.repoRecyclerView.adapter = this
            binding.repoRecyclerView.adapter = this.withLoadStateHeaderAndFooter(
                header = ReposLoadStateAdapter { this.retry() },
                footer = ReposLoadStateAdapter { this.retry() }
            )
            this.addLoadStateListener { loadState ->
                binding.swipeRefreshLayout.isRefreshing = loadState.refresh is LoadState.Loading
            }
        }

    }

    private fun setupListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener { userAdapter.refresh() }
    }

    private fun setupObservers() {
        viewModel.repos.observeForever {
            userAdapter.submitData(lifecycle, it)
        }
    }

    override fun onRepoClicked(binding: ItemRepoBinding, repo: Repo) {
        view?.findNavController()?.navigate(RepoFragmentDirections.repoDetails(repo))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // This ensures we keep the recyclerView at the same position when we navigate back from Details Fragment
        viewModel.listState = binding.repoRecyclerView.layoutManager?.onSaveInstanceState()
    }
}

