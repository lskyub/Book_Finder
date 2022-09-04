package com.bookfinder.custom

import android.text.Editable
import android.text.TextWatcher

/**
 * 사용자의 입력 데이터를 받기 위한 TextWatcher 클래스
 */
abstract class SearchTextWatcher: TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }
}