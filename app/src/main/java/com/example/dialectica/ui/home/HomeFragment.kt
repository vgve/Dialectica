package com.example.dialectica.ui.home

import android.annotation.SuppressLint
import android.app.Dialog
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
import com.example.dialectica.data.DialectQuestion
import com.example.dialectica.databinding.FragmentHomeBinding
import com.example.dialectica.data.DialectTheme
import com.example.dialectica.databinding.DialogRandomQuestionBinding
import com.example.dialectica.ui.adapters.QuestionListAdapter
import com.example.dialectica.ui.adapters.ThemeListAdapter
import com.example.dialectica.utils.TAG
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private var themesAdapter: ThemeListAdapter = ThemeListAdapter { viewModel.onClickTheme(it) }
    private var questionsAdapter: QuestionListAdapter = QuestionListAdapter { viewModel.onClickQuestion(it) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(this.TAG, "onViewCreated")

        observeUiState()
        _binding.rvThemes.adapter = themesAdapter
        _binding.rvQuestions.adapter = questionsAdapter
        _binding.ivMagicRandom.setOnClickListener {
            val randomQuestion = viewModel.uiState.value.allQuestions.random().textQuestion
            val dialogBinding = DialogRandomQuestionBinding.inflate(layoutInflater)
            val dialog = Dialog(requireContext()).apply {
                window?.setBackgroundDrawableResource(android.R.color.transparent)
                setContentView(dialogBinding.root)
                setCancelable(true)
            }
            dialogBinding.tvQuestion.text = randomQuestion
            dialog.show()
        }
        _binding.btnNext.setOnClickListener {
            viewModel.onClickNext()
        }
    }

    private fun observeUiState() {
        Log.d(this.TAG, "observeUiState")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    Log.d(this.TAG, "$state")
                    setThemeList(state.themeList)
                    setQuestionList(state.currentQuestionList)
                    _binding.tvQuestion.text = state.currentQuestion?.textQuestion
                    _binding.btnNext.isVisible = state.currentQuestion?.textQuestion != null
                    _binding.btnAddFav.isVisible = state.currentQuestion?.textQuestion != null
                    _binding.btnAddPersonal.isVisible = state.currentQuestion?.textQuestion != null
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setThemeList(themes: List<DialectTheme>) {
        themesAdapter.items = themes
        themesAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setQuestionList(questions: List<DialectQuestion>) {
        questionsAdapter.items = questions
        questionsAdapter.notifyDataSetChanged()
    }
}
