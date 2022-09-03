package com.bookfinder.custom

import android.text.Editable
import android.text.TextWatcher

abstract class SearchTextWatcher: TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }
}