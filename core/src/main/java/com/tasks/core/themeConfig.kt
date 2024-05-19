package com.tasks.core

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

fun initTheme(applicationContext: Context) {
    AppCompatDelegate.setDefaultNightMode(getThemeState(applicationContext))
}

fun switchTheme(applicationContext: Context, isDark: Boolean) {
    val newNightMode = if (isDark) {
        AppCompatDelegate.MODE_NIGHT_YES
    } else {
        AppCompatDelegate.MODE_NIGHT_NO
    }

    AppCompatDelegate.setDefaultNightMode(newNightMode)
    saveThemeState(applicationContext, newNightMode)
}


private fun saveThemeState(applicationContext: Context, themeMode: Int) {
    val sharedPreferences = applicationContext.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt(THEME_MODE_KEY, themeMode)
    editor.apply()
}

private fun getThemeState(applicationContext: Context, ): Int {
    val sharedPreferences = applicationContext.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE)
    return sharedPreferences.getInt(THEME_MODE_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
}


private const val THEME_PREF = "THEME_PREF"
private const val THEME_MODE_KEY = "THEME_MODE_KEY"
