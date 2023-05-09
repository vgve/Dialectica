package com.example.dialectica.ui.talk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.dialectica.databinding.FragmentTalkBinding

class TalkFragment : Fragment() {

    private lateinit var _binding: FragmentTalkBinding
    private val viewModel: TalkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTalkBinding.inflate(inflater, container, false)
        return _binding.root
    }
}
