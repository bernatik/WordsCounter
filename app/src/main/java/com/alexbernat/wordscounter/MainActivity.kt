package com.alexbernat.wordscounter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexbernat.wordscounter.presentation.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, MainFragment.newInstance())
                .commit()
        }
    }
}
