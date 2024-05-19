package com.tasks.core

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

fun validateMailPattern(context: Context, login: String): Boolean {
    val emailPattern = context.getString(R.string.mailPattern).toRegex()
    return emailPattern.matches(login)
}

fun validatePasswordPattern(password: String): Boolean {
    return password.length >= 6 && !password.contains(" ")
}

fun validateNamePattern(name: String): Boolean {
    return name.length >= 2 && !name.subSequence(0, 2).contains(" ")
}

fun <T> toastValidateAnyFiled(
    context: Context,
    value: T,
    reflectFunction: (T)->Boolean,
    badReflectString: String
) : Boolean {
    val resultOfValidate = reflectFunction(value)
    if (!resultOfValidate)
        Toast.makeText(
            context,
            badReflectString,
            Toast.LENGTH_LONG).show()
    return resultOfValidate
}


fun <T> toastValidateAnyFiled(
    context: Context,
    value: T,
    reflectFunction: (Context, T)->Boolean,
    badReflectString: String
) : Boolean {
    val resultOfValidate = reflectFunction(context, value)
    if (!resultOfValidate)
        Toast.makeText(
            context,
            badReflectString,
            Toast.LENGTH_LONG).show()
    return resultOfValidate
}


fun <T> alertValidateAnyFiled(
    context: Context,
    value: T,
    reflectFunction: (T)->Boolean,
    badReflectString: String,
    positiveButtonText: String = "Ok",
    positiveButtonAction: () -> Unit = {},
    negativeButtonText: String? = null,
    negativeButtonAction: () -> Unit = {}
) : Boolean {
    val resultOfValidate = reflectFunction(value)
    if (!resultOfValidate) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Ошибка")
            .setMessage(badReflectString)
            .setPositiveButton(positiveButtonText) { _, _ -> positiveButtonAction() }
        if (negativeButtonText != null)
            dialog.setNegativeButton(negativeButtonText) { _, _ -> negativeButtonAction() }
        dialog.create().show()
    }
    return resultOfValidate
}


fun <T> alertValidateAnyFiled(
    context: Context,
    value: T,
    reflectFunction: (Context, T)->Boolean,
    badReflectString: String,
    positiveButtonText: String = "Ok",
    positiveButtonAction: () -> Unit = {},
    negativeButtonText: String? = null,
    negativeButtonAction: () -> Unit = {}
) : Boolean {
    val resultOfValidate = reflectFunction(context, value)
    if (!resultOfValidate) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Ошибка")
            .setMessage(badReflectString)
            .setPositiveButton(positiveButtonText) { _, _ -> positiveButtonAction() }
        if (negativeButtonText != null)
            dialog.setNegativeButton(negativeButtonText) { _, _ -> negativeButtonAction() }
        dialog.create().show()
    }
    return resultOfValidate
}
