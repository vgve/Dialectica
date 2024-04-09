package com.example.dialectica.presentation.favourite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dialectica.R
import com.example.dialectica.databinding.FragmentFavouriteBinding
import com.example.dialectica.presentation.MyApplication
import com.example.dialectica.presentation.ui.adapters.QuestionListAdapter
import com.example.dialectica.utils.TAG
import com.example.dialectica.utils.viewModelFactory
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding

    private val viewModel: FavouriteViewModel by viewModels(
        factoryProducer = {
            viewModelFactory {
                FavouriteViewModel(
                    MyApplication.appModule.sharedPrefsRepository,
                    MyApplication.appModule.appRoomRepository
                )
            }
        }
    )

    private var questionsAdapter: QuestionListAdapter = QuestionListAdapter {
        Log.d(this.TAG, "onDeleteQuestion: $it")
        viewModel.onDeleteQuestion(it) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateFavourites()

        observeUiState()
    }

    private fun observeUiState() {
        Log.d(this.TAG, "observeUiState")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    with(binding) {
                        // Toolbar
                       toolbar.apply {
                            tvToolbarTitle.text = getString(R.string.favourites_fragment_title)
                        }

                        btnEmpty.isVisible = state.questions.isEmpty()

                        // List of questions
                        rvQuestions.apply {
                            adapter = questionsAdapter
                            questionsAdapter.items = state.questions
                            questionsAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }
}
