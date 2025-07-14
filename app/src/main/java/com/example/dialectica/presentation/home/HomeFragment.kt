package com.example.dialectica.presentation.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import com.example.dialectica.data.models.DialectTheme
import com.example.dialectica.databinding.DialogRandomQuestionBinding
import com.example.dialectica.presentation.MyApplication
import com.example.dialectica.presentation.ui.adapters.PersonAddListAdapter
import com.example.dialectica.presentation.ui.adapters.ThemeListAdapter
import com.example.dialectica.utils.TAG
import com.example.dialectica.utils.viewModelFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels(
        factoryProducer = {
            viewModelFactory {
                HomeViewModel(
                    MyApplication.appModule.sharedPrefsRepository,
                    MyApplication.appModule.appRoomRepository
                )
            }
        }
    )

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
                    viewModel.deleteFavourite() {
                        Toast.makeText(
                            context,
                            getString(R.string.successful_deleted),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    viewModel.addToFavourite() {
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
            viewModel.onClickAddToTalk()
        }
        _binding.fabMagicRandom.setOnClickListener {
            viewModel.onClickRandom()
            val isFavourite =
                viewModel.uiState.value.favouriteList
                    .contains(viewModel.uiState.value.currentRandomQuestion)
            val dialogBinding = DialogRandomQuestionBinding.inflate(layoutInflater)
            val dialog = Dialog(requireContext()).apply {
                window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
                setContentView(dialogBinding.root)
                setCancelable(true)
            }
            dialog.show()
            dialogBinding.tvQuestion.text = viewModel.uiState.value.currentRandomQuestion?.text
            dialogBinding.btnAddFav.isVisible = !isFavourite
            dialogBinding.btnAddFav.setOnClickListener {
                viewModel.addToFavourite() {
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
                viewModel.onClickAddToTalk()
                dialog.dismiss()
            }
        }
    }

    private fun observeUiState() {
        Log.d(this.TAG, "observeUiState")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    Log.d(this.TAG, "$state")
                    with(_binding) {
                        tvStart.text =
                            if (state.isAuthorize) getString(R.string.info_home_page_user, state.username)
                            else getString(R.string.info_home_page)
                        setThemeList(state.themeList)
                        tvQuestion.text = state.currentQuestion?.text
                        tvQuestion.isVisible = state.currentQuestion != null
                        tvStart.isVisible = state.currentQuestion == null
                        btnNext.isVisible = state.currentQuestion?.text != null
                        btnAddFav.isVisible = state.currentQuestion?.text != null
                        btnAddPersonal.isVisible = state.currentQuestion?.text != null
                        val favIcon = if (state.isFavourite) R.drawable.ic_fav_click else R.drawable.ic_fav_menu
                        btnAddFav.setImageResource(favIcon)
                        val personIconColor = if (state.personList.isEmpty()) R.color.grey else R.color.black
                        btnAddPersonal.setColorFilter(
                            ContextCompat.getColor(
                                requireContext(),
                                personIconColor
                            )
                        )

                        if (state.currentQuestion != null) {
                            viewModel.changeFavouriteState()
                        }
                    }
                }
            }
        }
    }

    private fun observeUIAction() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiAction.collect { uiAction ->
                    when (uiAction) {
                        is HomeAction.AddQuestionToPersonClick -> {
                            Toast.makeText(context, getString(R.string.successful_added), Toast.LENGTH_SHORT).show()
                        }
                        is HomeAction.OpenPersonalScreen -> {
                            findNavController().navigate(R.id.action_navigation_home_to_navigation_personal)
                        }
                        is HomeAction.ShowAddToTalkScreen -> {
                            showAddToTalkDialog()
                        }
                    }
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

    private fun showAddToTalkDialog() {
        Log.d(TAG, "showAddToTalkDialog")
        if (viewModel.uiState.value.personList.isEmpty()) {
            Toast.makeText(context, getString(R.string.need_to_add_person), Toast.LENGTH_SHORT).show()
            return
        }
        val dialogBinding = DialogAddToTalkBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
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
            dialog.dismiss()
        }

        dialogBinding.rvPersons.adapter = personsAdapter
        personsAdapter.items = viewModel.uiState.value.personList
    }
}
