package com.jacksonueda.test.ui.user.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jacksonueda.test.data.model.Repo
import com.jacksonueda.test.databinding.UserDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

private const val REPO = "user"

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(repo: Repo) = UserDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(REPO, repo)
            }
        }
    }

    private lateinit var repoDetails: Repo
    private val viewModel: UserDetailViewModel by viewModels()
    private lateinit var binding: UserDetailFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            repoDetails = it.getParcelable<Repo>(REPO) as Repo
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = UserDetailFragmentBinding.inflate(inflater).apply {
        binding = this
        lifecycleOwner = this@UserDetailFragment
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.repo = repoDetails
    }

}

