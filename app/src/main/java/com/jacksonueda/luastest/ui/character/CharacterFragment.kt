package com.jacksonueda.luastest.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.jacksonueda.luastest.data.model.Character
import com.jacksonueda.luastest.databinding.ItemCharacterBinding
import com.jacksonueda.luastest.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@AndroidEntryPoint
class CharacterFragment : Fragment(), CharacterAdapter.CharacterClickListener {

    companion object {
        /**
         * Creates an entry point to generate a new instance of the CharacterFragment.
         */
        fun newInstance() = CharacterFragment()
    }

    @Inject
    lateinit var characterAdapter: CharacterAdapter

    private val viewModel: CharacterViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupListeners()
        setupData()
    }

    /**
     * Function responsible to set up the binding variables or views to the Fragment.
     */
    private fun setupAdapter() {
        with(binding) {
            characterRecyclerView.adapter = characterAdapter
            swipeRefreshLayout.setOnRefreshListener { characterAdapter.refresh() }
        }
    }

    private fun setupListeners() {
        characterAdapter.characterClickListener = this@CharacterFragment

        characterAdapter.addLoadStateListener {
            binding.swipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
        }
    }

    @ExperimentalCoroutinesApi
    private fun setupData() {
        compositeDisposable.add(
            viewModel.characters.subscribe {
                characterAdapter.submitData(lifecycle, it)
            }
        )
    }

    override fun onCharacterClicked(binding: ItemCharacterBinding, character: Character) {

    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }
}

