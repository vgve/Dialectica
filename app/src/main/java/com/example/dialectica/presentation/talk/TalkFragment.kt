package com.example.dialectica.presentation.talk

import android.annotation.SuppressLint
import android.app.Dialog
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
import com.example.dialectica.R
import com.example.dialectica.databinding.DialogDeleteBinding
import com.example.dialectica.databinding.DialogEnterNewInfoBinding
import com.example.dialectica.databinding.DialogRandomQuestionBinding
import com.example.dialectica.databinding.FragmentTalkBinding
import com.example.dialectica.data.models.entity.DialectQuestion
import com.example.dialectica.presentation.MyApplication
import com.example.dialectica.presentation.ui.adapters.InterestLocalListAdapter
import com.example.dialectica.presentation.ui.adapters.QuestionListAdapter
import com.example.dialectica.utils.PERSON_ID
import com.example.dialectica.utils.TAG
import com.example.dialectica.utils.viewModelFactory
import kotlinx.coroutines.launch

class TalkFragment : Fragment() {

    private lateinit var _binding: FragmentTalkBinding
    private val viewModel: TalkViewModel by viewModels(
        factoryProducer = {
            viewModelFactory {
                TalkViewModel(MyApplication.appModule.appRoomRepository)
            }
        }
    )

    private var interestsAdapter: InterestLocalListAdapter = InterestLocalListAdapter {
        Log.d(this.TAG, "onClickTheme: $it")
        viewModel.onDeleteInterest(it)
    }

    private var questionsAdapter: QuestionListAdapter = QuestionListAdapter()

    private val swipeToDismissTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val deletedCourse = viewModel.uiState.value.questions[viewHolder.adapterPosition]
            viewModel.onDeleteQuestion(deletedCourse) {}
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

        viewModel.getPerson(arguments?.getInt(PERSON_ID)) { }

        _binding.rvInterests.adapter = interestsAdapter

        _binding.rvQuestions.adapter = questionsAdapter

        _binding.btnAddInterest.setOnClickListener {
            showAddInterestDialog()
        }

        _binding.btnAddNewQuestion.setOnClickListener {
            showAddNewQuestionDialog()
        }

        _binding.fabMagicRandom.setOnClickListener {
            val randomQuestion = viewModel.getRandom()
            val dialogBinding = DialogRandomQuestionBinding.inflate(layoutInflater)
            val dialog = Dialog(requireContext()).apply {
                window?.setBackgroundDrawableResource(android.R.color.transparent)
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
                text = getString(R.string.back)
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
                    setInterestList(state.interestList)
                    setQuestionList(state.questions)
                    _binding.tvOwnInterests.text = getString(R.string.interests, state.username)
                    _binding.tvForOwner.text = getString(R.string.info_for_owner, state.username)
                    _binding.cvHello.isVisible = !state.isOwner
                    _binding.cvForOwner.isVisible = state.isOwner
                    _binding.fabMagicRandom.isVisible = state.questions.size > 1
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setInterestList(interestList: List<LocalInterest>) {
        Log.d(this.TAG, "setOwnInterestList")
        interestsAdapter.items = interestList
        interestsAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setQuestionList(questionList: List<DialectQuestion>) {
        Log.d(this.TAG, "setOwnInterestList")
        questionsAdapter.items = questionList
        questionsAdapter.notifyDataSetChanged()
        swipeToDismissTouchHelper.attachToRecyclerView(_binding.rvQuestions)
    }

    private fun showAddInterestDialog() {
        val dialogBinding = DialogEnterNewInfoBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
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
            window?.setBackgroundDrawableResource(android.R.color.transparent)
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

    private fun onDeleteQuestion(question: DialectQuestion) {
        val dialogBinding = DialogDeleteBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
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
