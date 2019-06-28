package com.martdev.android.expensetrackr

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {
    private var mFullName: TextInputEditText? = null
    private var mEmail: TextInputEditText? = null
    private var mPassword: TextInputEditText? = null
    private var mConfirmPassword: TextInputEditText? = null
    private var mRegisterButton: Button? = null
    private var mLogin: TextView? = null
    private var mRegisterBar: ProgressBar? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity)

        mAuth = FirebaseAuth.getInstance()

        init()
        mRegisterButton!!.setOnClickListener(View.OnClickListener { v ->
            val email = mEmail!!.text!!.toString().trim { it <= ' ' }
            val password = mPassword!!.text!!.toString().trim { it <= ' ' }
            val confirmPassword = mConfirmPassword!!.text!!.toString().trim { it <= ' ' }

            if (validation(email, password, confirmPassword)) return@OnClickListener

            mRegisterBar!!.visibility = View.VISIBLE
            mAuth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@RegistrationActivity) { task ->
                        Log.i(TAG, "onComplete:" + task.isSuccessful)
                        mRegisterBar!!.visibility = View.GONE
                        if (task.isSuccessful) {
                            Snackbar.make(v, "Registration Complete",
                                    Snackbar.LENGTH_LONG).show()
                        } else {
                            Snackbar.make(v, "Registration Fail",
                                    Snackbar.LENGTH_LONG).show()
                        }
                    }
        })

        mLogin!!.setOnClickListener { finish() }
    }

    private fun init() {
        mFullName = findViewById(R.id.full_name_field)
        mEmail = findViewById(R.id.registration_email)
        mPassword = findViewById(R.id.registration_password)
        mConfirmPassword = findViewById(R.id.confirm_password)
        mRegisterBar = findViewById(R.id.register_bar)
        mRegisterBar!!.visibility = View.GONE
        mRegisterButton = findViewById(R.id.register_button)
        mLogin = findViewById(R.id.login_text)
    }

    private fun validation(email: String, password: String, confirmPassword: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            mEmail!!.error = "Email is empty"
            return true
        } else if (!emailValidator(email)) {
            mEmail!!.error = "Invalid email"
            return true
        }

        if (TextUtils.isEmpty(password)) {
            mPassword!!.error = "Password is empty"
            return true
        } else if (password.length < 6) {
            mPassword!!.error = "At least 6 characters"
            return true
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            mConfirmPassword!!.error = "Confirm Password"
            return true
        }

        if (!confirmPassword.contains(password)) {
            mConfirmPassword!!.error = "Not a match"
            return true
        }
        return false
    }

    private fun emailValidator(email: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val emailPattern = "^[_A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*@[a-z]" + "+(\\.[a-z]+)*(\\.[a-z]{3,})$"
        pattern = Pattern.compile(emailPattern)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }

    companion object {

        private val TAG = RegistrationActivity::class.java.simpleName
    }
}
