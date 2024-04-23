package com.example.dialectica.presentation.ui.adapters

import com.example.dialectica.data.models.entity.DialectPerson
import com.example.dialectica.data.models.entity.DialectQuestion
import com.example.dialectica.data.models.DialectTheme
import com.example.dialectica.presentation.talk.LocalInterest
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class ThemeListAdapter(
    itemClickedListener: (DialectTheme) -> Unit
) : ListDelegationAdapter<List<DialectTheme>>(
    themeAdapterDelegate(itemClickedListener)
)

class QuestionListAdapter : ListDelegationAdapter<List<DialectQuestion>>(
    questionAdapterDelegate()
)

class InterestListAdapter(
    itemDeleteClickedListener: (String) -> Unit
) : ListDelegationAdapter<List<String>>(
    interestAdapterDelegate(itemDeleteClickedListener)
)

class InterestLocalListAdapter(
    itemDeleteClickedListener: (LocalInterest) -> Unit
) : ListDelegationAdapter<List<LocalInterest>>(
    interestLocalAdapterDelegate(itemDeleteClickedListener)
)

class PersonListAdapter(
    itemClickedListener: (DialectPerson) -> Unit,
    itemDeleteClickedListener: (DialectPerson) -> Unit
) : ListDelegationAdapter<List<DialectPerson>>(
    personAdapterDelegate(itemClickedListener, itemDeleteClickedListener)
)

class PersonAddListAdapter(
    itemClickedListener: (DialectPerson) -> Unit
) : ListDelegationAdapter<List<DialectPerson>>(
    personAddAdapterDelegate(itemClickedListener)
)
