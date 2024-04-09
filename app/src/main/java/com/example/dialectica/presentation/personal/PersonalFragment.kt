package com.example.dialectica.presentation.personal

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
import com.example.dialectica.databinding.DialogDeleteBinding
import com.example.dialectica.databinding.DialogEnterNewInfoBinding
import com.example.dialectica.databinding.DialogLoginBinding
import com.example.dialectica.databinding.FragmentPersonalBinding
import com.example.dialectica.data.models.entity.DialectPerson
import com.example.dialectica.presentation.MyApplication
import com.example.dialectica.presentation.ui.adapters.InterestListAdapter
import com.example.dialectica.presentation.ui.adapters.PersonListAdapter
import com.example.dialectica.utils.PERSON_ID
import com.example.dialectica.utils.TAG
import com.example.dialectica.utils.viewModelFactory
import kotlinx.coroutines.launch

class PersonalFragment : Fragment() {

    private lateinit var _binding: FragmentPersonalBinding
    private val viewModel: PersonalViewModel by viewModels(
        factoryProducer = {
            viewModelFactory {
                PersonalViewModel(
                    MyApplication.appModule.sharedPrefsRepository,
                    MyApplication.appModule.appRoomRepository
                )
            }
        }
    )

    private var ownInterestsAdapter: InterestListAdapter = InterestListAdapter {
        Log.d(this.TAG, "onClickTheme: $it")
        viewModel.onDeleteOwnInterest(it)
    }

    private var interestsNewUserAdapter: InterestListAdapter = InterestListAdapter {
        Log.d(this.TAG, "onClickTheme: $it")
        viewModel.onDeleteInterestOfUser(it)
    }

    private var personsAdapter: PersonListAdapter = PersonListAdapter (
        {
            Log.d(this.TAG, "onClickPerson: $it")
            val bundle = Bundle()
            bundle.putInt(PERSON_ID, it.id)
            findNavController().navigate(R.id.action_navigation_personal_to_navigation_talk, bundle)
        },
        {
            Log.d(this.TAG, "onDeletePerson: $it")
            onDeletePerson(it)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUiState()

        viewModel.getPersons()

        _binding.rvPersons.adapter = personsAdapter
        _binding.rvOwnInterests.adapter = ownInterestsAdapter

        _binding.btnAddPerson.setOnClickListener {
            showAddPersonDialog()
        }

        _binding.btnAddInterest.setOnClickListener {
            showAddInterestDialog()
        }
    }

    private fun observeUiState() {
        Log.d(this.TAG, "observeUiState")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    Log.d(this.TAG, "$state")
                    _binding.rvPersons.isVisible = state.personList.isNotEmpty()
                    setOwnInterestList(state.ownInterestList)
                    setNewUserInterestList(state.tempInterestList)
                    setPersonList(state.personList)
                    _binding.btnLogin.isVisible = !state.isAuthorized
                    _binding.tvInfo.isVisible = !state.isAuthorized
                    _binding.btnAddPerson.isVisible = state.isAuthorized
                    _binding.cvHello.isVisible = state.isAuthorized
                    _binding.tvOwnInterests.text = getString(R.string.own_interests, state.username)

                    _binding.btnLogin.apply {
                        setOnClickListener {
                            if (!state.isAuthorized) showLoginUserDialog()
                        }
                    }

                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setOwnInterestList(ownInterestList: List<String>) {
        Log.d(this.TAG, "setOwnInterestList")
        ownInterestsAdapter.items = ownInterestList
        ownInterestsAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setNewUserInterestList(interestList: List<String>) {
        Log.d(this.TAG, "setOwnInterestList")
        interestsNewUserAdapter.items = interestList
        interestsNewUserAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setPersonList(persons: List<DialectPerson>) {
        Log.d(this.TAG, "setThemeList")
        personsAdapter.items = persons
        personsAdapter.notifyDataSetChanged()
    }

    private fun showLoginUserDialog() {
        val dialogBinding = DialogLoginBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setContentView(dialogBinding.root)
            setCancelable(true)
        }
        dialog.show()
        dialogBinding.rvOwnInterests.adapter = ownInterestsAdapter
        dialogBinding.btnLogin.setOnClickListener {
            val username = dialogBinding.etName.text.toString().trim()
            if (username.isNotEmpty()) {
                viewModel.loginUser(username)
                dialog.dismiss()
            } else {
                Toast.makeText(context, getString(R.string.error_empty_name), Toast.LENGTH_SHORT).show()
            }
        }
        dialogBinding.btnReturn.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.ivAddInterest.apply {
            setOnClickListener {
                viewModel.addOwnInterest(dialogBinding.etInterests.text.toString().trim())
                dialogBinding.etInterests.setText("")
            }
        }
    }

    private fun showAddPersonDialog() {
        val dialogBinding = DialogLoginBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setContentView(dialogBinding.root)
            setCancelable(true)
        }
        dialog.show()
        dialogBinding.rvOwnInterests.adapter = interestsNewUserAdapter
        dialogBinding.btnLogin.text = getString(R.string.dialog_btn_add)
        dialogBinding.btnLogin.setOnClickListener {
            val username = dialogBinding.etName.text.toString().trim()
            if (username.isNotEmpty()) {
                viewModel.insertPerson(username) {}
                dialog.dismiss()
            } else {
                Toast.makeText(context, getString(R.string.error_empty_name), Toast.LENGTH_SHORT).show()
            }
        }
        dialogBinding.btnReturn.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.ivAddInterest.apply {
            setOnClickListener {
                viewModel.addInterestOfUser(dialogBinding.etInterests.text.toString().trim())
                dialogBinding.etInterests.setText("")
            }
        }
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
            viewModel.addOwnInterest(dialogBinding.etInfo.text.toString().trim())
            dialog.dismiss()
        }
    }

    private fun onDeletePerson(person: DialectPerson) {
        val dialogBinding = DialogDeleteBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext()).apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setContentView(dialogBinding.root)
            setCancelable(true)
        }
        dialog.show()

        dialogBinding.tvInfo.text = getString(R.string.info_delete_person, person.name)

        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnYes.setOnClickListener {
            viewModel.onDeletePerson(person) {
                dialog.dismiss()
            }
        }
    }
}

