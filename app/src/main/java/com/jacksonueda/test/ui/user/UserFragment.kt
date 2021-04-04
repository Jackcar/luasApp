package com.jacksonueda.test.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.jacksonueda.test.R
import com.jacksonueda.test.data.model.User
import com.jacksonueda.test.databinding.ItemUserBinding
import com.jacksonueda.test.databinding.UserFragmentBinding
import com.jacksonueda.test.ui.user.detail.UserDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class UserFragment : Fragment(), UserAdapter.UserClickListener {

    companion object {
        fun newInstance() = UserFragment()
    }

    private lateinit var binding: UserFragmentBinding

    private val userAdapter = UserAdapter(this)
    private val viewModel: UserViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()

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
        setupData()
    }

    /**
     * Function responsible to set up the binding variables or views to the Fragment.
     */
    private fun setupBindings() {
        with(binding) {
            userRecyclerView.adapter = userAdapter
            swipeRefreshLayout.setOnRefreshListener { userAdapter.refresh() }
        }
    }

    private fun setupListeners() {
        userAdapter.addLoadStateListener {
            binding.swipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
        }
    }

    @ExperimentalCoroutinesApi
    private fun setupData() {
        compositeDisposable.add(
            viewModel.users.subscribe {
                userAdapter.submitData(lifecycle, it)
            }
        )
    }

    override fun onUserClicked(binding: ItemUserBinding, user: User) {
        parentFragmentManager.beginTransaction()
            .add(R.id.container, UserDetailFragment.newInstance(user))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }
}

