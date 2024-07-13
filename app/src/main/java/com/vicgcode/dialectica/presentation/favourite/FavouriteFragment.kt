package com.vicgcode.dialectica.presentation.favourite

import android.app.Dialog
import android.graphics.Canvas
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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.vicgcode.dialectica.R
import com.vicgcode.dialectica.data.models.entity.DialectQuestion
import com.vicgcode.dialectica.databinding.DialogDeleteBinding
import com.vicgcode.dialectica.databinding.FragmentFavouriteBinding
import com.vicgcode.dialectica.presentation.MyApplication
import com.vicgcode.dialectica.presentation.extensions.TAG
import com.vicgcode.dialectica.presentation.ui.adapters.QuestionListAdapter
import com.vicgcode.dialectica.utils.SWIPE_DX
import com.vicgcode.dialectica.utils.viewModelFactory
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

    private var questionsAdapter: QuestionListAdapter = QuestionListAdapter()

    private val swipeToDismissTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            // Return element in list after swiping
            val newDx = if (dX >= SWIPE_DX) SWIPE_DX else dX
            super.onChildDraw(c, recyclerView, viewHolder, newDx, dY, actionState, isCurrentlyActive)
        }
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            binding.rvQuestions.adapter?.notifyItemChanged(viewHolder.absoluteAdapterPosition)
            val deletedElement = viewModel.uiState.value.questions[viewHolder.absoluteAdapterPosition]
            viewModel.onSwipeToDeleteQuestion(deletedElement)
        }
    })

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

        observeUIAction()
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
                            swipeToDismissTouchHelper.attachToRecyclerView(this)
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
                        is FavouriteAction.OpenPopupToDeleteQuestion -> {
                            openPopupToDeleteQuestion(uiAction.question)
                        }
                    }
                }
            }
        }
    }

    private fun openPopupToDeleteQuestion(question: DialectQuestion) {
        val dialogBinding = DialogDeleteBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
            setContentView(dialogBinding.root)
            setCancelable(true)
        }
        dialog.show()

        dialogBinding.tvInfo.text = getString(R.string.info_delete_question, question.text)

        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnYes.setOnClickListener {
            viewModel.onDeleteQuestion(question) {
                dialog.dismiss()
            }
        }
    }
}
