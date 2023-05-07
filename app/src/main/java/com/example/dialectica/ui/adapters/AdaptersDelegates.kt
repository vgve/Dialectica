package com.example.dialectica.ui.adapters

import com.example.dialectica.R
import com.example.dialectica.data.DialectQuestion
import com.example.dialectica.databinding.ItemThemeBinding
import com.example.dialectica.data.DialectTheme
import com.example.dialectica.databinding.ItemQuestionBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun themeAdapterDelegate(
    itemClickedListener: (DialectTheme) -> Unit
) = adapterDelegateViewBinding<DialectTheme, DialectTheme, ItemThemeBinding>(
    { layoutInflater, root -> ItemThemeBinding.inflate(layoutInflater, root, false) }
) {
    binding.linearFieldItemTheme.setOnClickListener {
        itemClickedListener(item)
    }
    bind {
        val strokeColor = if (item.isChosen == true) R.color.black else R.color.white
        binding.itemThemeTitle.text = item.nameTheme
        binding.itemThemeImage.setImageResource(item.srcTheme)
        binding.itemThemeImage.setStrokeColorResource(strokeColor)
    }
}

fun questionAdapterDelegate(
    itemClickedListener: (DialectQuestion) -> Unit
) = adapterDelegateViewBinding<DialectQuestion, DialectQuestion, ItemQuestionBinding>(
    { layoutInflater, root -> ItemQuestionBinding.inflate(layoutInflater, root, false) }
) {
    binding.ivCheck.setOnClickListener {
        itemClickedListener(item)
    }
    bind {
        if (item.isChosen == true) {
            binding.ivCheck.setImageResource(R.drawable.ic_chosen)
        }
        binding.tvQuestionText.text = item.textQuestion
    }
}
