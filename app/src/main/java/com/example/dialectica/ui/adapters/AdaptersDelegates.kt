package com.example.dialectica.ui.adapters

import com.example.dialectica.R
import com.example.dialectica.models.DialectQuestion
import com.example.dialectica.databinding.ItemThemeBinding
import com.example.dialectica.models.DialectTheme
import com.example.dialectica.databinding.ItemQuestionBinding
import com.example.dialectica.models.Themes
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
    itemDeleteClickedListener: (DialectQuestion) -> Unit
) = adapterDelegateViewBinding<DialectQuestion, DialectQuestion, ItemQuestionBinding>(
    { layoutInflater, root -> ItemQuestionBinding.inflate(layoutInflater, root, false) }
) {
    binding.ivDelete.setOnClickListener {
        itemDeleteClickedListener(item)
    }
    bind {
            var themeOfQuestionIcon: Int? = null
            Themes().themeList.forEach {
                if (item.idTheme == it.id) {
                    themeOfQuestionIcon = it.srcTheme
                }
            }
            binding.ivTheme.setImageResource(themeOfQuestionIcon ?: R.drawable.ic_hello)

        binding.tvQuestionText.text = item.textQuestion
    }
}
