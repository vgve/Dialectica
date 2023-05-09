package com.example.dialectica.ui.adapters

import com.example.dialectica.models.DialectPerson
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

class InterestListAdapter(
    itemDeleteClickedListener: (String) -> Unit
) : ListDelegationAdapter<List<String>>(
    interestAdapterDelegate(itemDeleteClickedListener)
)

class PersonListAdapter(
    itemClickedListener: (DialectPerson) -> Unit,
    itemDeleteClickedListener: (DialectPerson) -> Unit
) : ListDelegationAdapter<List<DialectPerson>>(
    personAdapterDelegate(itemClickedListener, itemDeleteClickedListener)
)
