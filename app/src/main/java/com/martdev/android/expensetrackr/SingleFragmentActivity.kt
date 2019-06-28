package com.martdev.android.expensetrackr

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

abstract class SingleFragmentActivity : AppCompatActivity() {

    abstract fun createFragment(): Fragment

    abstract fun setToolbarTitle(actionBar: ActionBar?)

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
        setToolbarTitle(actionBar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}