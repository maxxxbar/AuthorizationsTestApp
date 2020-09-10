package com.worldshine.authorizationstestapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var emailsArr = mutableListOf<String>()
    private val testText = "qwe@qwe.qwe"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewCreate.visibility = View.GONE
        emailsArr.add(testText)

    }

    fun onCLickButton(view: View) {
        val email = activityMainTextfieldEmail.text?.trim().toString()
        val password = activityMainTextfieldPassword.text?.trim().toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            var s = ""
            for (q in emailsArr) {
                if (q == email) {
                    s = q
                }
            }
            if (s.isNotEmpty()) {
                Toast.makeText(this, "Email: $email Есть в массиве", Toast.LENGTH_SHORT).show()
                textViewCreate.visibility = View.GONE
            } else {
                Toast.makeText(this, "Email: $email нет массиве", Toast.LENGTH_SHORT).show()
                textViewCreate.visibility = View.VISIBLE
            }
        }
    }

    fun onClickCreate(view: View) {
        val email = activityMainTextfieldEmail.text?.trim().toString()
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(buttonDo.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        emailsArr.add(email)
        view.visibility = View.GONE
/*
        Toast.makeText(this, "Email: $email успешно добавлен", Toast.LENGTH_SHORT).show()
*/
        Snackbar.make(
            findViewById<View>(android.R.id.content),
            "Email: $email успешно добавлен",
            Snackbar.LENGTH_LONG
        ).show()
    }
}




