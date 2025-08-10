package com.vicgcode.dialectica.presentation.ui.adapters

import com.vicgcode.dialectica.data.models.entity.DialectPerson
import com.vicgcode.dialectica.data.models.entity.DialectQuestion
import com.vicgcode.dialectica.data.models.DialectTheme
import com.vicgcode.dialectica.presentation.screens.conversation.LocalInterest
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
    itemClickedListener: (DialectPerson) -> Unit
) : ListDelegationAdapter<List<DialectPerson>>(
    personAdapterDelegate(itemClickedListener)
)

class PersonAddListAdapter(
    itemClickedListener: (DialectPerson) -> Unit
) : ListDelegationAdapter<List<DialectPerson>>(
    personAddAdapterDelegate(itemClickedListener)
)
