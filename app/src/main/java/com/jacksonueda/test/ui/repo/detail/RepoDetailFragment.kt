package com.jacksonueda.test.ui.repo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.jacksonueda.test.R
import com.jacksonueda.test.data.model.Repo
import com.jacksonueda.test.databinding.RepoDetailFragmentBinding
import com.jacksonueda.test.ui.repo.ReposLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RepoDetailFragment : Fragment() {

    private lateinit var repoDetails: Repo

    private val args: RepoDetailFragmentArgs by navArgs()
    private val viewModel: RepoDetailViewModel by viewModels()

    private lateinit var binding: RepoDetailFragmentBinding
    private val issuesAdapter = RepoIssuesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repoDetails = args.repo
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RepoDetailFragmentBinding.inflate(inflater).apply {
        binding = this
        lifecycleOwner = this@RepoDetailFragment
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.repo = repoDetails

        setupToolbar()
        setupAdapter()
        setupObservers()
        setupListeners()

        viewModel.getIssues(repoDetails.owner.login, repoDetails.name)
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).apply {
            supportActionBar?.title = repoDetails.name
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupListeners() {
        binding.retryButton.setOnClickListener { issuesAdapter.retry() }
    }

    private fun setupAdapter() {
        with(issuesAdapter) {
            binding.issuesRecyclerView.adapter = issuesAdapter

            binding.issuesRecyclerView.adapter = this.withLoadStateHeaderAndFooter(
                header = ReposLoadStateAdapter { this.retry() },
                footer = ReposLoadStateAdapter { this.retry() }
            )

            this.addLoadStateListener { loadState ->
                // show empty list text
                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && issuesAdapter.itemCount == 0
                showNoIssuesText(isListEmpty)

                // Only show the list if refresh succeeds.
                binding.issuesRecyclerView.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

                // Toast on any error
                val errorState = loadState.refresh as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        requireContext(),
                        "Wooops, ${it.error.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setupObservers() {
        viewModel.issues.observeForever {
            issuesAdapter.submitData(lifecycle, it)
        }
    }

    private fun showNoIssuesText(show: Boolean) {
        if (show) {
            binding.noIssuesText.visibility = View.VISIBLE
            binding.issuesRecyclerView.visibility = View.GONE
        } else {
            binding.noIssuesText.visibility = View.GONE
            binding.issuesRecyclerView.visibility = View.VISIBLE
        }
    }

}

