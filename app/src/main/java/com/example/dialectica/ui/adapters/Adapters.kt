package com.example.dialectica.ui.adapters

import com.example.dialectica.models.DialectQuestion
import com.example.dialectica.models.DialectTheme
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class ThemeListAdapter(
    itemClickedListener: (DialectTheme) -> Unit
) : ListDelegationAdapter<List<DialectTheme>>(
    themeAdapterDelegate(itemClickedListener)
)

class QuestionListAdapter(
    itemDeleteClickedListener: (DialectQuestion) -> Unit
) : ListDelegationAdapter<List<DialectQuestion>>(
    questionAdapterDelegate(itemDeleteClickedListener)
)
