package com.example.dialectica.ui.adapters

import com.example.dialectica.data.DialectQuestion
import com.example.dialectica.data.DialectTheme
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class ThemeListAdapter(
    itemClickedListener: (DialectTheme) -> Unit
) : ListDelegationAdapter<List<DialectTheme>>(
    themeAdapterDelegate(itemClickedListener)
)

class QuestionListAdapter(
    itemClickedListener: (DialectQuestion) -> Unit
) : ListDelegationAdapter<List<DialectQuestion>>(
    questionAdapterDelegate(itemClickedListener)
)
