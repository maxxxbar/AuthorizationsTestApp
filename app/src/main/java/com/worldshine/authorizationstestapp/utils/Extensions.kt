package com.worldshine.authorizationstestapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun createSnackbar(view: View, text: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, text, length).show()
}

fun String.isValidEmail(): Boolean {
    val reg =
        Regex(pattern = "[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    return reg.matches(this)
}

fun String.isValidPassword(): Boolean {
    val reg = Regex(pattern = "^(?=.*\\d)(?=.*[a-zа-яё])(?=.*[A-ZА-ЯЁ])(?=.*[A-ZА-ЯЁ]).{6,}\$")
    return reg.matches(this)
}