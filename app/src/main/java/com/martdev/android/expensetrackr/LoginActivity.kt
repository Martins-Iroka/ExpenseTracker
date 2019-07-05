package com.martdev.android.expensetrackr

import android.content.Intent
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.martdev.android.expensetrackr.monthlyexpenses.MonthlyExpensesActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var mEmail: TextInputEditText
    private lateinit var mPassword: TextInputEditText
    private lateinit var mLoginButton: Button
    private lateinit var mRegisterText: TextView
    private lateinit var mLoginProgress: ProgressBar
    private lateinit var mLayout: ConstraintLayout

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        mAuth = FirebaseAuth.getInstance()

        init()

        if (mAuth.currentUser != null) {
            val email = mAuth.currentUser!!.email
            mEmail.setText(email)
            Snackbar.make(mLayout, "Welcome " + email!!, Snackbar.LENGTH_SHORT).show()
        }

        mLoginButton.setOnClickListener(View.OnClickListener { v ->
            val email = mEmail.text!!.toString().trim { it <= ' ' }
            val password = mPassword.text!!.toString().trim { it <= ' ' }

            if (validation(email, password)) return@OnClickListener

            mLoginProgress.visibility = View.VISIBLE

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        Log.i(TAG, "onComplete:" + task.isSuccessful)
                        mLoginProgress.visibility = View.GONE
                        if (task.isSuccessful) {
                            Intent(this, MonthlyExpensesActivity::class.java).apply {
                                startActivity(this)
                            }
                        } else {
                            Snackbar.make(v, "Failed to Login",
                                    Snackbar.LENGTH_LONG).show()
                        }
                    }
        })

        mRegisterText.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java)) }
    }

    private fun init() {
        mEmail = findViewById(R.id.login_email)
        mPassword = findViewById(R.id.login_password)
        mLoginButton = findViewById(R.id.login_button)
        mRegisterText = findViewById(R.id.register_text)
        mLoginProgress = findViewById(R.id.login_bar)
        mLoginProgress.visibility = View.GONE
        mLayout = findViewById(R.id.layout)
    }

    private fun validation(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            mEmail.error = "Email is empty"
            return true
        }

        if (TextUtils.isEmpty(password)) {
            mPassword.error = "Password is empty"
            return true
        }
        return false
    }

    companion object {

        private val TAG = LoginActivity::class.java.simpleName
    }
}
