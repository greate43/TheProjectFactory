package com.sk.greate43.theprojectfactory.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sk.greate43.theprojectfactory.OnSelectedStateListener
import com.sk.greate43.theprojectfactory.R

class MainActivity : AppCompatActivity(), OnSelectedStateListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
//        setSupportActionBar(findViewById(R.id.toolbar))
//        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is MainFragment) {
            fragment.setOnSelectedStateListener(this)
        } else if (fragment is SelectedThingsFragment) {
            fragment.setOnSelectedStateListener(this)
        }
    }

    override fun onSelected(state: String) {

    }
}