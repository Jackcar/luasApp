package com.jacksonueda.luastest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jacksonueda.luastest.ui.forecast.ForecastFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ForecastFragment.newInstance())
                    .commitNow()
        }
    }
}