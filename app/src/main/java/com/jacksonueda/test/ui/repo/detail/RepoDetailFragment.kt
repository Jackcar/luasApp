package com.jacksonueda.test.ui.repo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.jacksonueda.test.data.model.Repo
import com.jacksonueda.test.databinding.RepoDetailFragmentBinding
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

        setupBindings()
        setupObservers()

        repoDetails.apply {
            binding.repo = this
            viewModel.getIssues(this.owner.login, this.name)
        }
    }

    private fun setupBindings() {
        binding.issuesRecyclerView.adapter = issuesAdapter
    }

    private fun setupObservers() {
        viewModel.issues.observeForever {
            issuesAdapter.submitData(lifecycle, it)
        }
    }

}

