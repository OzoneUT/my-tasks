package com.kafleyozone.mytasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        with(supportActionBar) {
            this?.elevation = 0f
            this?.title = "Tasks"
        }
    }
}