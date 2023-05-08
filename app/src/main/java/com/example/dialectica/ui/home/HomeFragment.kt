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
import androidx.navigation.fragment.findNavController
import com.example.dialectica.R
import com.example.dialectica.databinding.DialogLoginBinding
import com.example.dialectica.databinding.FragmentHomeBinding
import com.example.dialectica.models.DialectTheme
import com.example.dialectica.databinding.DialogRandomQuestionBinding
import com.example.dialectica.ui.adapters.ThemeListAdapter
import com.example.dialectica.utils.AppPreference
import com.example.dialectica.utils.TAG
import com.example.dialectica.utils.TYPE_ROOM
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private var themesAdapter: ThemeListAdapter = ThemeListAdapter {
        Log.d(this.TAG, "onClickTheme: $it")
        viewModel.onClickTheme(it)
    }

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
        viewModel.getFavQuestions()

        _binding.rvThemes.adapter = themesAdapter
        _binding.btnNext.setOnClickListener {
            viewModel.onClickNext()
        }
        _binding.btnAddFav.apply {
            setOnClickListener {
                viewModel.addToFavourite(viewModel.uiState.value.currentQuestion) {}
            }
        }
        _binding.btnAddPersonal.setOnClickListener {

        }
        _binding.ivMagicRandom.setOnClickListener {
            val randomQuestion = viewModel.onClickRandom()
            val isFavourite = viewModel.uiState.value.favouriteList.contains(randomQuestion)
            val dialogBinding = DialogRandomQuestionBinding.inflate(layoutInflater)
            val dialog = Dialog(requireContext()).apply {
                window?.setBackgroundDrawableResource(android.R.color.transparent)
                setContentView(dialogBinding.root)
                setCancelable(true)
            }
            dialog.show()
            dialogBinding.tvQuestion.text = randomQuestion?.text
            dialogBinding.btnAddFav.isVisible = !isFavourite
            dialogBinding.btnAddFav.setOnClickListener {
                viewModel.addToFavourite(randomQuestion) {}
                dialog.dismiss()
            }
            dialogBinding.btnAddPersonal.setOnClickListener {
                viewModel.addToPersonal(viewModel.uiState.value.currentQuestion) {}
                dialog.dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (AppPreference.getInitUser()) {
            viewModel.initDatabase(AppPreference.getTypeDatabase()) {}
        } else {
            initDatabase()
        }
    }

    private fun observeUiState() {
        Log.d(this.TAG, "observeUiState")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    Log.d(this.TAG, "$state")
                    setThemeList(state.themeList)
                    _binding.tvQuestion.text = state.currentQuestion?.text
                    _binding.btnNext.isVisible = state.currentQuestion?.text != null
                    _binding.btnAddFav.isVisible = state.currentQuestion?.text != null
                    _binding.btnAddPersonal.isVisible = state.currentQuestion?.text != null
                    _binding.btnAddFav.text = if (state.isFavourite) getString(R.string.is_fav) else getString(R.string.add_to_fav)
                    val ic = if (state.isFavourite) R.drawable.ic_check else R.drawable.ic_fav_menu
                    _binding.btnAddFav.setIconResource(ic)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setThemeList(themes: List<DialectTheme>) {
        Log.d(this.TAG, "setThemeList")
        themesAdapter.items = themes
        themesAdapter.notifyDataSetChanged()
    }

    private fun checkInitUser(): Boolean {
        Log.d(TAG, "checkInitUser: ${AppPreference.getInitUser()}")
        if (!AppPreference.getInitUser()) {
            val dialogBinding = DialogLoginBinding.inflate(layoutInflater)
            val dialog = Dialog(requireContext()).apply {
                window?.setBackgroundDrawableResource(android.R.color.transparent)
                setContentView(dialogBinding.root)
                setCancelable(true)
            }
            dialog.show()
            dialogBinding.btnNo.setOnClickListener {
                dialog.dismiss()
            }
            dialogBinding.btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_home_to_navigation_personal)
                dialog.dismiss()
            }
            return false
        }
        return true
    }

    private fun initDatabase() {
        viewModel.initDatabase(TYPE_ROOM) {
            AppPreference.setInitUser(true)
            AppPreference.setTypeDatabase(TYPE_ROOM)
        }
    }
}
