package com.vicgcode.dialectica.presentation.talk

import android.app.Dialog
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.vicgcode.dialectica.R
import com.vicgcode.dialectica.databinding.DialogDeleteBinding
import com.vicgcode.dialectica.databinding.DialogEnterNewInfoBinding
import com.vicgcode.dialectica.databinding.DialogRandomQuestionBinding
import com.vicgcode.dialectica.databinding.FragmentTalkBinding
import com.vicgcode.dialectica.data.models.entity.DialectQuestion
import com.vicgcode.dialectica.presentation.extensions.TAG
import com.vicgcode.dialectica.presentation.extensions.setOnSingleClickListener
import com.vicgcode.dialectica.presentation.ui.adapters.InterestLocalListAdapter
import com.vicgcode.dialectica.presentation.ui.adapters.QuestionListAdapter
import com.vicgcode.dialectica.utils.SWIPE_DX
import kotlinx.coroutines.launch

class TalkFragment : Fragment() {

    private lateinit var _binding: FragmentTalkBinding

    private val viewModel: TalkViewModel by viewModels()

    private var interestsAdapter: InterestLocalListAdapter = InterestLocalListAdapter {
        Log.d(this.TAG, "onClickTheme: $it")
        viewModel.onDeleteInterest(it)
    }

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
            _binding.rvQuestions.adapter?.notifyItemChanged(viewHolder.absoluteAdapterPosition)
            val deletedElement = viewModel.uiState.value.questions[viewHolder.absoluteAdapterPosition]
            viewModel.onSwipeToDeleteQuestion(deletedElement)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTalkBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUiState()

        observeUIAction()

        _binding.rvInterests.adapter = interestsAdapter

        _binding.rvQuestions.adapter = questionsAdapter

        _binding.btnAddInterest.setOnSingleClickListener {
            showAddInterestDialog()
        }

        _binding.btnAddNewQuestion.setOnSingleClickListener {
            showAddNewQuestionDialog()
        }

        _binding.fabMagicRandom.setOnSingleClickListener {
            val randomQuestion = viewModel.getRandom()
            val dialogBinding = DialogRandomQuestionBinding.inflate(layoutInflater)
            val dialog = Dialog(requireContext()).apply {
                window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
                setContentView(dialogBinding.root)
                setCancelable(true)
            }
            dialog.show()
            dialogBinding.tvQuestion.text = randomQuestion?.text
            dialogBinding.btnAddFav.isVisible = false
            dialogBinding.btnAddPersonal.isVisible = false
            dialogBinding.btnAddFav.isVisible = false
            dialogBinding.btnAddPersonal.isVisible = true
            dialogBinding.btnAddPersonal.apply {
                setIconResource(R.drawable.ic_return)
                text = getString(R.string.common_back)
                setOnClickListener {
                    dialog.dismiss()
                }
            }
        }
    }

    private fun observeUiState() {
        Log.d(this.TAG, "observeUiState")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    with(_binding) {
                        // Interests
                        interestsAdapter.items = state.interestList
                        interestsAdapter.notifyDataSetChanged()

                        // Questions
                        questionsAdapter.items = state.questions
                        questionsAdapter.notifyDataSetChanged()
                        swipeToDismissTouchHelper.attachToRecyclerView(rvQuestions)

                        tvOwnInterests.text = getString(R.string.interests, state.username)
                        tvForOwner.text = getString(R.string.info_for_owner, state.username)
                        cvHello.isVisible = !state.isOwner
                        cvForOwner.isVisible = state.isOwner
                        fabMagicRandom.isVisible = state.questions.size > 1
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
                        is TalkAction.OpenPopupToDeleteQuestion -> {
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

    private fun showAddInterestDialog() {
        val dialogBinding = DialogEnterNewInfoBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
            setContentView(dialogBinding.root)
            setCancelable(true)
        }
        dialog.show()

        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnYes.setOnClickListener {
            viewModel.addInterest(dialogBinding.etInfo.text.toString().trim())
            dialog.dismiss()
        }
    }

    private fun showAddNewQuestionDialog() {
        val dialogBinding = DialogEnterNewInfoBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
            setContentView(dialogBinding.root)
            setCancelable(true)
        }
        dialog.show()

        dialogBinding.tvQuestion.text = getString(R.string.info_new_question)
        dialogBinding.tilUsername.isVisible = false
        dialogBinding.tilQuestion.isVisible = true

        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnYes.setOnClickListener {
            viewModel.addNewQuestion(dialogBinding.etQuestion.text.toString().trim()) {}
            dialog.dismiss()
        }
    }
}
