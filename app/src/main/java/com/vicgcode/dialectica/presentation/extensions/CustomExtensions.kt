package com.vicgcode.dialectica.presentation.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.vicgcode.dialectica.utils.OnSingleClickListener

val Any.TAG: String
    get() = this::class.java.simpleName

fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        @Suppress("EmptyFunctionBlock")
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        @Suppress("EmptyFunctionBlock")
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}
