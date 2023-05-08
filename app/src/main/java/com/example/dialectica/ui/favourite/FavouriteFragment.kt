package com.example.dialectica.ui.favourite

import android.annotation.SuppressLint
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
import com.example.dialectica.databinding.FragmentFavouriteBinding
import com.example.dialectica.models.DialectQuestion
import com.example.dialectica.ui.adapters.QuestionListAdapter
import com.example.dialectica.utils.AppPreference
import com.example.dialectica.utils.TAG
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment() {

    private lateinit var _binding: FragmentFavouriteBinding
    private val viewModel: FavouriteViewModel by viewModels()

    private var questionsAdapter: QuestionListAdapter = QuestionListAdapter {
        Log.d(this.TAG, "onDeleteQuestion: $it")
        viewModel.onDeleteQuestion(it) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.rvQuestions.apply {
            isVisible = AppPreference.getInitUser()
            adapter = questionsAdapter
        }

        viewModel.getFavQuestions()
        observeUiState()
    }

    private fun observeUiState() {
        Log.d(this.TAG, "observeUiState")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    setQuestionList(state.questions)
                    _binding.btnEmpty.isVisible = state.questions.isEmpty()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setQuestionList(questions: List<DialectQuestion>) {
        Log.d(TAG, "setQuestionList: $questions")
        questionsAdapter.items = questions
        questionsAdapter.notifyDataSetChanged()
    }
}
