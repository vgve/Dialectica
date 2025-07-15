package com.vicgcode.dialectica.presentation.home

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
import com.vicgcode.dialectica.R
import com.vicgcode.dialectica.databinding.DialogAddToTalkBinding
import com.vicgcode.dialectica.databinding.FragmentHomeBinding
import com.vicgcode.dialectica.databinding.DialogRandomQuestionBinding
import com.vicgcode.dialectica.presentation.MyApplication
import com.vicgcode.dialectica.presentation.extensions.TAG
import com.vicgcode.dialectica.presentation.extensions.navigateSafely
import com.vicgcode.dialectica.presentation.extensions.setOnSingleClickListener
import com.vicgcode.dialectica.presentation.ui.adapters.PersonAddListAdapter
import com.vicgcode.dialectica.presentation.ui.adapters.ThemeListAdapter
import com.vicgcode.dialectica.utils.viewModelFactory
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
    }

    @Suppress("LongMethod")
    private fun observeUiState() {
        Log.d(this.TAG, "observeUiState")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    Log.d(this.TAG, "$state")
                    with(_binding) {
                        rvThemes.adapter = themesAdapter
                        themesAdapter.items = state.sections
                        themesAdapter.notifyDataSetChanged()

                        btnNext.setOnClickListener { viewModel.onClickNext() }

                        btnAddFav.apply {
                            val favIcon = if (state.isFavourite) R.drawable.ic_fav_click else R.drawable.ic_fav_menu
                            setImageResource(favIcon)
                            isVisible = state.currentQuestion?.text != null
                            setOnSingleClickListener {
                                viewModel.uiState.value.currentQuestion?.let {
                                    if (viewModel.uiState.value.isFavourite) {
                                        viewModel.deleteFavourite(it) {
                                            Toast.makeText(
                                                context,
                                                getString(R.string.successful_deleted),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    } else {
                                        viewModel.addToFavourite(it) {
                                            Toast.makeText(
                                                context,
                                                getString(R.string.successful_added),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            }
                        }

                        btnAddPersonal.apply {
                            isVisible = state.currentQuestion?.text != null && state.personList.isNotEmpty()
                            setOnSingleClickListener {
                                viewModel.setRandomState(false)
                                viewModel.onClickAddToTalk()
                            }
                        }

                        fabMagicRandom.setOnSingleClickListener {
                            val randomQuestion = viewModel.onClickRandom()
                            val isFavourite = viewModel.uiState.value.favouriteList.contains(randomQuestion)
                            val dialogBinding = DialogRandomQuestionBinding.inflate(layoutInflater)
                            val dialog = Dialog(requireContext()).apply {
                                window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
                                setContentView(dialogBinding.root)
                                setCancelable(true)
                            }
                            dialog.show()
                            dialogBinding.tvQuestion.text = randomQuestion?.text
                            dialogBinding.btnAddFav.isVisible = !isFavourite
                            dialogBinding.btnAddFav.setOnSingleClickListener {
                                randomQuestion?.let {
                                    viewModel.addToFavourite(it) {
                                        Toast.makeText(
                                            context,
                                            getString(R.string.successful_added),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                dialog.dismiss()
                            }
                            dialogBinding.btnAddPersonal.setOnSingleClickListener {
                                viewModel.setRandomState(true)
                                viewModel.onClickAddToTalk()
                                dialog.dismiss()
                            }
                        }

                        tvStart.text =
                            if (state.isAuthorize) getString(R.string.info_home_page_user, state.username)
                            else getString(R.string.info_home_page)


                        tvQuestion.text = state.currentQuestion?.text
                        tvQuestion.isVisible = state.currentQuestion != null
                        tvStart.isVisible = state.currentQuestion == null
                        btnNext.isVisible = state.currentQuestion?.text != null

                        if (state.currentQuestion != null) { viewModel.changeFavouriteState() }
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
                            findNavController().navigateSafely(R.id.action_navigation_home_to_navigation_personal)
                        }
                        is HomeAction.ShowAddToTalkScreen -> {
                            showAddToTalkDialog()
                        }
                    }
                }
            }
        }
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

        val personsAdapter = PersonAddListAdapter {
            Log.d(this.TAG, "onClickPerson: $it")
            viewModel.addQuestionToPerson(it) { }
            dialog.dismiss()
        }

        dialogBinding.rvPersons.adapter = personsAdapter
        personsAdapter.items = viewModel.uiState.value.personList
    }
}
