package com.example.dialectica.ui.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.dialectica.R
import com.example.dialectica.databinding.DialogAddToTalkBinding
import com.example.dialectica.databinding.FragmentHomeBinding
import com.example.dialectica.models.DialectTheme
import com.example.dialectica.databinding.DialogRandomQuestionBinding
import com.example.dialectica.ui.adapters.PersonAddListAdapter
import com.example.dialectica.ui.adapters.ThemeListAdapter
import com.example.dialectica.utils.*
import kotlinx.coroutines.flow.onEach
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

        observeUIAction()

        viewModel.getFavQuestions()
        viewModel.getPersons()

        _binding.rvThemes.adapter = themesAdapter
        _binding.btnNext.setOnClickListener {
            viewModel.onClickNext()
        }
        _binding.btnAddFav.apply {
            setOnClickListener {
                if (viewModel.uiState.value.isFavourite) {
                    viewModel.deleteFavourite(viewModel.uiState.value.currentQuestion) {
                        Toast.makeText(
                            context,
                            getString(R.string.successful_deleted),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    viewModel.addToFavourite(viewModel.uiState.value.currentQuestion) {
                        Toast.makeText(
                            context,
                            getString(R.string.successful_added),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        _binding.btnAddPersonal.setOnClickListener {
            viewModel.setRandomState(false)
            checkUserAuthorize()
        }
        _binding.fabMagicRandom.setOnClickListener {
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
                viewModel.addToFavourite(randomQuestion) {
                    Toast.makeText(
                        context,
                        getString(R.string.successful_added),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                dialog.dismiss()
            }
            dialogBinding.btnAddPersonal.setOnClickListener {
                viewModel.setRandomState(true)
                checkUserAuthorize()
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

        if (AppPreference.getUserAuthorize()) {
            _binding.tvStart.text = if (AppPreference.getUserAuthorize()) getString(R.string.info_home_page_user, AppPreference.getUserName()) else getString(R.string.info_home_page)
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
                    _binding.tvQuestion.isVisible = state.currentQuestion != null
                    _binding.tvStart.isVisible = state.currentQuestion == null
                    _binding.btnNext.isVisible = state.currentQuestion?.text != null
                    _binding.btnAddFav.isVisible = state.currentQuestion?.text != null
                    _binding.btnAddPersonal.isVisible = state.currentQuestion?.text != null
                    val ic = if (state.isFavourite) R.drawable.ic_fav_click else R.drawable.ic_fav_menu
                    _binding.btnAddFav.setImageResource(ic)

                    if (state.currentQuestion != null) {
                        viewModel.changeFavouriteState()
                    }

                }
            }
        }
    }

    private fun observeUIAction() {
        viewModel.uiAction.onEach { uiAction ->
            when (uiAction) {
                is HomeAction.AddQuestionToPersonClick -> {
                    Log.d(TAG, "HomeAction.AddQuestionToPersonClick")
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

    private fun checkUserAuthorize() {
        Log.d(TAG, "checkUserAuthorize: ${AppPreference.getUserName()}")
        if (AppPreference.getUserName().isEmpty() || AppPreference.getUserName() == USER_QUEST) {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_personal)
        } else {
            showAddToTalkDialog()
        }
    }

    private fun showAddToTalkDialog() {
        val dialogBinding = DialogAddToTalkBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setContentView(dialogBinding.root)
            setCancelable(true)
        }
        dialog.show()

        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }

        val personsAdapter= PersonAddListAdapter {
            Log.d(this.TAG, "onClickPerson: $it")
            viewModel.addQuestionToPerson(it) { }
            Toast.makeText(context, getString(R.string.successful_added), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialogBinding.rvPersons.adapter = personsAdapter
        personsAdapter.items = viewModel.uiState.value.personList
    }

    private fun initDatabase() {
        viewModel.initDatabase(TYPE_ROOM) {
            AppPreference.setInitUser(true)
            AppPreference.setTypeDatabase(TYPE_ROOM)
        }
    }
}
