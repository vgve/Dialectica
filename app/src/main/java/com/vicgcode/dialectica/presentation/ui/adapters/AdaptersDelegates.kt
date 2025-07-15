package com.vicgcode.dialectica.presentation.ui.adapters

import androidx.core.content.ContextCompat
import com.vicgcode.dialectica.R
import com.vicgcode.dialectica.databinding.ItemInterestBinding
import com.vicgcode.dialectica.databinding.ItemPersonBinding
import com.vicgcode.dialectica.data.models.entity.DialectQuestion
import com.vicgcode.dialectica.databinding.ItemThemeBinding
import com.vicgcode.dialectica.data.models.DialectTheme
import com.vicgcode.dialectica.databinding.ItemQuestionBinding
import com.vicgcode.dialectica.data.models.entity.DialectPerson
import com.vicgcode.dialectica.data.models.Themes
import com.vicgcode.dialectica.presentation.talk.LocalInterest
import com.vicgcode.dialectica.utils.LOCALE_RU
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import java.util.*

fun themeAdapterDelegate(
    itemClickedListener: (DialectTheme) -> Unit
) = adapterDelegateViewBinding<DialectTheme, DialectTheme, ItemThemeBinding>(
    { layoutInflater, root -> ItemThemeBinding.inflate(layoutInflater, root, false) }
) {
    binding.linearFieldItemTheme.setOnClickListener {
        itemClickedListener(item)
    }
    bind {
        val strokeColor = if (item.isChosen == true) R.color.black else R.color.light_grey
        binding.itemThemeTitle.text = item.name
        binding.itemThemeImage.setImageResource(item.src)
        binding.itemThemeImage.setStrokeColorResource(strokeColor)
    }
}

fun questionAdapterDelegate() = adapterDelegateViewBinding<DialectQuestion, DialectQuestion, ItemQuestionBinding>(
    { layoutInflater, root -> ItemQuestionBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        var themeOfQuestionIcon: Int? = null
        val sections = if (Locale.getDefault().language == LOCALE_RU) Themes.ruSections else Themes.enSections
        sections.forEach {
            if (item.idTheme == it.id) {
                themeOfQuestionIcon = it.src
            }
        }
        binding.ivTheme.setImageResource(themeOfQuestionIcon ?: R.drawable.ic_inkwell)

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

fun interestLocalAdapterDelegate(
    itemDeleteClickedListener: (LocalInterest) -> Unit
) = adapterDelegateViewBinding<LocalInterest, LocalInterest, ItemInterestBinding>(
    { layoutInflater, root -> ItemInterestBinding.inflate(layoutInflater, root, false) }
) {
    binding.btnDelete.setOnClickListener {
        itemDeleteClickedListener(item)
    }
    bind {
        binding.tvInterest.text = item.name
        val backgroundColor =
            if (item.isCommon) ContextCompat.getColor(context, R.color.lavender) else ContextCompat.getColor(
                context,
                R.color.light_grey
            )
        binding.llInterest.setBackgroundColor(backgroundColor)
    }
}

fun personAdapterDelegate(
    itemClickedListener: (DialectPerson) -> Unit
) = adapterDelegateViewBinding<DialectPerson, DialectPerson, ItemPersonBinding>(
    { layoutInflater, root -> ItemPersonBinding.inflate(layoutInflater, root, false) }
) {
    binding.itemQuestion.setOnClickListener {
        itemClickedListener(item)
    }
    bind {
        binding.tvPersonName.text = if (!item.isOwner) item.name else getString(R.string.notes)
        val iconPerson = if (!item.isOwner) R.drawable.ic_person_menu else R.drawable.ic_inkwell
        binding.ivPerson.setImageResource(iconPerson)
    }
}

fun personAddAdapterDelegate(
    itemClickedListener: (DialectPerson) -> Unit
) = adapterDelegateViewBinding<DialectPerson, DialectPerson, ItemPersonBinding>(
    { layoutInflater, root -> ItemPersonBinding.inflate(layoutInflater, root, false) }
) {
    binding.llPerson.setOnClickListener {
        itemClickedListener(item)
    }
    bind {
        binding.tvPersonName.text = if (!item.isOwner) item.name else getString(R.string.notes)
        val iconPerson = if (!item.isOwner) R.drawable.ic_person_menu else R.drawable.ic_inkwell
        binding.ivPerson.setImageResource(iconPerson)
    }
}
