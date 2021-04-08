package com.jacksonueda.test.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.jacksonueda.test.R
import com.jacksonueda.test.data.model.Repo
import com.jacksonueda.test.databinding.ItemRepoBinding
import com.jacksonueda.test.databinding.UserFragmentBinding
import com.jacksonueda.test.ui.user.detail.UserDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserFragment : Fragment(), RepoAdapter.UserClickListener {

    companion object {
        fun newInstance() = UserFragment()
    }

    private lateinit var binding: UserFragmentBinding

    private val userAdapter = RepoAdapter(this)
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = UserFragmentBinding.inflate(inflater).apply {
        binding = this
        lifecycleOwner = this@UserFragment
    }.root

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
        setupListeners()
        setupObservers()

        viewModel.getUsers()
    }

    /**
     * Function responsible to set up the binding variables or views to the Fragment.
     */
    private fun setupBindings() {
        binding.userRecyclerView.adapter = userAdapter
    }

    private fun setupListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener { userAdapter.refresh() }
        userAdapter.addLoadStateListener {
            binding.swipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
        }
    }

    private fun setupObservers() {
        viewModel.repos.observeForever {
            userAdapter.submitData(lifecycle, it)
        }
    }

    override fun onUserClicked(binding: ItemRepoBinding, repo: Repo) {
        parentFragmentManager.beginTransaction()
            .add(R.id.container, UserDetailFragment.newInstance(repo))
            .addToBackStack("userDetails")
            .commit()
    }

}

