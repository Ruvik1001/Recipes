package com.tasks.core

import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager

fun hideInputBoard(applicationContext: Context, windowToken: IBinder) {
    val inputMethodManager = applicationContext
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}