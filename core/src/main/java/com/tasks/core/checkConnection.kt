package com.tasks.core

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.google.firebase.FirebaseApp

fun isNetworkAvailable(applicationContext: Context): Boolean {
    val connectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        return networkInfo.isConnected
    }
}

fun toastCheckConnection(applicationContext: Context): Boolean {
    if (!toastValidateAnyFiled(
        context = applicationContext,
        value = applicationContext,
        reflectFunction = ::isNetworkAvailable,
        badReflectString = applicationContext.getString(R.string.networkError)
    )
    ) return false
    return true
}

fun alertCheckConnection(applicationContext: Context): Boolean {
    if (!alertValidateAnyFiled(
            context = applicationContext,
            value = applicationContext,
            reflectFunction = ::isNetworkAvailable,
            badReflectString = applicationContext.getString(R.string.networkError)
        )
    ) return false
    return true
}

fun checkInternet(applicationContext: Context) {
    if (!isNetworkAvailable(applicationContext)) {
        AlertDialog.Builder(applicationContext)
            .setTitle(applicationContext.getString(com.tasks.core.R.string.networkErrorTitle))
            .setMessage(com.tasks.core.R.string.networkError)
            .setPositiveButton(applicationContext.getString(com.tasks.core.R.string.reloadText)) { _, _ ->

            }
            .setOnDismissListener { checkInternet(applicationContext)  }
            .create().show()
    }
}
