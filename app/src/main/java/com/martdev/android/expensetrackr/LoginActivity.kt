package com.martdev.android.expensetrackr

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
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

class LoginActivity : AppCompatActivity() {
    private var mEmail: TextInputEditText? = null
    private var mPassword: TextInputEditText? = null
    lateinit var mLoginButton: Button
    private var mRegisterText: TextView? = null
    private var mLoginProgress: ProgressBar? = null
    private var mLayout: ConstraintLayout? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        mAuth = FirebaseAuth.getInstance()

        init()

        if (mAuth!!.currentUser != null) {
            val email = mAuth!!.currentUser!!.email
            mEmail!!.setText(email)
            Snackbar.make(mLayout!!, "Welcome " + email!!, Snackbar.LENGTH_SHORT).show()
        }

        mLoginButton.setOnClickListener(View.OnClickListener { v ->
            val email = mEmail!!.text!!.toString().trim { it <= ' ' }
            val password = mPassword!!.text!!.toString().trim { it <= ' ' }

            if (validation(email, password)) return@OnClickListener

            mLoginProgress!!.visibility = View.VISIBLE

            mAuth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        Log.i(TAG, "onComplete:" + task.isSuccessful)
                        mLoginProgress!!.visibility = View.GONE
                        if (task.isSuccessful) {
                            Snackbar.make(v, "Login successful",
                                    Snackbar.LENGTH_LONG).show()
                        } else {
                            Snackbar.make(v, "Failed to Login",
                                    Snackbar.LENGTH_LONG).show()
                        }
                    }
        })

        mRegisterText!!.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java)) }
    }

    private fun init() {
        mEmail = findViewById(R.id.login_email)
        mPassword = findViewById(R.id.login_password)
        mLoginButton = findViewById(R.id.login_button)
        mRegisterText = findViewById(R.id.register_text)
        mLoginProgress = findViewById(R.id.login_bar)
        mLoginProgress!!.visibility = View.GONE
        mLayout = findViewById(R.id.layout)
    }

    private fun validation(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            mEmail!!.error = "Email is empty"
            return true
        }

        if (TextUtils.isEmpty(password)) {
            mPassword!!.error = "Password is empty"
            return true
        }
        return false
    }

    companion object {

        private val TAG = LoginActivity::class.java.simpleName
    }
}
