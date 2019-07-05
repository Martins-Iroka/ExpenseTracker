package com.martdev.android.expensetrackr

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class SingleFragmentActivity : AppCompatActivity() {

    abstract fun createFragment(): Fragment

    abstract fun setToolbarTitle(toolbar: Toolbar, actionBar: ActionBar?)

    open fun setupPresenter(fragment: Fragment) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        val manager = supportFragmentManager
        var fragment = manager.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            fragment = createFragment()
            manager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }

        setupPresenter(fragment)
        setToolbarTitle(toolbar, actionBar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}