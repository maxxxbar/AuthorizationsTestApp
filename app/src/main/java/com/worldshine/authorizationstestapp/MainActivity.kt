package com.worldshine.authorizationstestapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.worldshine.authorizationstestapp.api.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private var compositeDisposable = CompositeDisposable()
    private var emailsArr = mutableListOf<String>()
    private val testText = "qwe@qwe.qwe"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewCreate.visibility = View.GONE
        emailsArr.add(testText)
        activityMainTextInputLayoutPassword.setEndIconOnClickListener {
            Toast.makeText(
                applicationContext,
                getString(R.string.password_error),
                Toast.LENGTH_LONG
            ).show()
        }
        mainLayout.setOnTouchListener { view, _ ->
            view.performClick()
            hideKeyboard()
            return@setOnTouchListener true
        }
    }

    fun onCLickButton(view: View) {
        val email =
            activityMainTextfieldEmail.text?.trim().toString().toLowerCase(Locale.getDefault())
        val password = activityMainTextfieldPassword.text?.trim().toString()
        activityMainTextInputLayoutEmail.error = null
        activityMainTextInputLayoutPassword.error = null
        if (email.isEmpty() || password.isEmpty() || !email.isValidEmail() || !password.isValidPassword()) {
            if (email.isEmpty()) {
                activityMainTextInputLayoutEmail.error = getString(R.string.email_empty)
            } else if (!email.isValidEmail()) {
                activityMainTextInputLayoutEmail.error = getString(R.string.email_error)
            }
            if (password.isEmpty()) {
                activityMainTextInputLayoutPassword.error = getString(R.string.password_empty)
            } else if (!password.isValidPassword()) {
                activityMainTextInputLayoutPassword.error = getString(R.string.password_error)
            }
        } else if (email.isNotEmpty() && password.isNotEmpty() && email.isValidEmail() && password.isValidPassword()) {
            if (findEmailInArray(email)) {
                textViewCreate.visibility = View.GONE
            } else {
                textViewCreate.visibility = View.VISIBLE
            }
            hideKeyboard()
            val disposable = ApiService.create.getForecast()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val forecast = it.let {
                        it.dailyForecasts?.get(0)?.temperature?.maximum?.value?.toInt().toString()
                    }
                    createSnackbar(
                        findViewById(android.R.id.content),
                        String.format(
                            getString(R.string.saransk),
                            forecast
                        )
                    )
                }, {

                    Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
                })
            compositeDisposable.add(disposable)
        }
    }

    fun onClickCreate(view: View) {
        val email =
            activityMainTextfieldEmail.text?.trim().toString().toLowerCase(Locale.getDefault())
        hideKeyboard()
        if (findEmailInArray(email)) {
            view.visibility = View.GONE
            createSnackbar(view, String.format(getString(R.string.email_alread_exists), email))
        } else if (email.isNotEmpty() && email.isValidEmail()) {
            emailsArr.add(email)
            view.visibility = View.GONE
            createSnackbar(view, String.format(getString(R.string.email_successfully_added), email))
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mainLayout.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun findEmailInArray(email: String): Boolean {
        for (q in emailsArr) {
            if (q == email) {
                return true
            }
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}

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




