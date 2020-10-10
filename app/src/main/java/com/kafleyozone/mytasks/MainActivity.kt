package com.kafleyozone.mytasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.kafleyozone.mytasks.fragments.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (findViewById<FrameLayout>(R.id.fragment_container) != null) {
            if (savedInstanceState != null) return // avoids bugs due to fragment overlaps
            supportFragmentManager.commit {
                add<HomeFragment>(R.id.fragment_container, null, intent.extras)
            }
        }
    }
}