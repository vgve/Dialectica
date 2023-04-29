package com.example.dialectica.ui.adapters

import com.example.dialectica.databinding.ItemThemeBinding
import com.example.dialectica.themes.DialectTheme
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
        binding.itemThemeTitle.text = item.name
        binding.itemThemeImage.setImageResource(item.src)
    }
}
