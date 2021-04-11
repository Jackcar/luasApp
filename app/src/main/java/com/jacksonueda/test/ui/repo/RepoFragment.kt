package com.jacksonueda.test.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RepoFragment : Fragment(), RepoAdapter.RepoClickListener {

    private lateinit var user: String
    private lateinit var binding: RepoFragmentBinding

    private val repoAdapter = RepoAdapter(this)

    // As the new Android Navigation does not hold the fragment state on the back navigation,
    // we hold it in the ViewModel so we can retrieve it later
    private val viewModel: RepoViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = "google"
    }

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

        setupToolbar()
        setupAdapter()
        setupListeners()
        setupObservers()

        if (viewModel.listState != null) {
            binding.repoRecyclerView.layoutManager?.onRestoreInstanceState(viewModel.listState)
            viewModel.listState = null
        } else {
            viewModel.getRepos(user)
        }
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).apply {
            supportActionBar?.title =
                getString(R.string.repo_title, user.capitalize(Locale.getDefault()))
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    /**
     * Function responsible to set up the adapter and its states
     */
    private fun setupAdapter() {
        with(repoAdapter) {
            binding.repoRecyclerView.adapter = this
            binding.repoRecyclerView.adapter = this.withLoadStateHeaderAndFooter(
                header = ReposLoadStateAdapter { this.retry() },
                footer = ReposLoadStateAdapter { this.retry() }
            )
            this.addLoadStateListener { loadState ->
                binding.swipeRefreshLayout.isRefreshing =
                    loadState.source.refresh is LoadState.Loading

                // Show the retry state if initial load/refresh fails and there is no item in the list.
                with(loadState.source.refresh is LoadState.Error && repoAdapter.itemCount == 0) {
                    binding.retryIcon.isVisible = this
                    binding.retryText.isVisible = this
                }

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

    private fun setupListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener { repoAdapter.refresh() }
    }

    private fun setupObservers() {
        viewModel.repos.observeForever {
            repoAdapter.submitData(lifecycle, it)
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

