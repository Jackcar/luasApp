package com.jacksonueda.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jacksonueda.test.ui.repo.RepoFragment
import dagger.hilt.android.AndroidEntryPoint


/**
 * Very first Activity to be loaded and responsible for holding the fragments.
 * Initialize with the UserFragment.
 *
 * Annotated with @AndroidEntryPoint to generate an individual Hilt component for each
 * Android class in your project. These components can receive dependencies from their
 * respective parent classes.
 *
 * Android classes that depend on this Activity should also be annotated with @AndroidEntryPoint
 * in order to receive the dependencies.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

    }

}