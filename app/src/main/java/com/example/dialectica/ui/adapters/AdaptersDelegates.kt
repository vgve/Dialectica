package com.example.dialectica.ui.adapters

import androidx.core.view.isVisible
import com.example.dialectica.R
import com.example.dialectica.databinding.ItemInterestBinding
import com.example.dialectica.databinding.ItemPersonBinding
import com.example.dialectica.models.DialectQuestion
import com.example.dialectica.databinding.ItemThemeBinding
import com.example.dialectica.models.DialectTheme
import com.example.dialectica.databinding.ItemQuestionBinding
import com.example.dialectica.models.DialectPerson
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
        binding.itemThemeTitle.text = item.name
        binding.itemThemeImage.setImageResource(item.src)
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
                    themeOfQuestionIcon = it.src
                }
            }
            binding.ivTheme.setImageResource(themeOfQuestionIcon ?: R.drawable.ic_hello)

        binding.tvQuestionText.text = item.text
    }
}

fun interestAdapterDelegate(
    itemClickedListener: (String) -> Unit
) = adapterDelegateViewBinding(
    { layoutInflater, root -> ItemInterestBinding.inflate(layoutInflater, root, false) }
) {
    binding.btnDelete.setOnClickListener {
        itemClickedListener(item)
    }
    bind {
        binding.tvInterest.text = item
    }
}

fun personAdapterDelegate(
    itemClickedListener: (DialectPerson) -> Unit,
    itemDeleteClickedListener: (DialectPerson) -> Unit
) = adapterDelegateViewBinding<DialectPerson, DialectPerson, ItemPersonBinding>(
    { layoutInflater, root -> ItemPersonBinding.inflate(layoutInflater, root, false) }
) {
    binding.itemQuestion.setOnClickListener {
        itemClickedListener(item)
    }
    binding.ivDelete.setOnClickListener {
        itemDeleteClickedListener(item)
    }
    bind {
        binding.tvPersonName.text = if (!item.isOwner) item.name else getString(R.string.notes)
        val iconPerson = if (!item.isOwner) R.drawable.ic_person_menu else R.drawable.ic_inkwell
        binding.ivPerson.setImageResource(iconPerson)
        binding.ivDelete.isVisible = !item.isOwner
    }
}
