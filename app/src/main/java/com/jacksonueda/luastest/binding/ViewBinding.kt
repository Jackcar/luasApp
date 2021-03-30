package com.jacksonueda.luastest.binding

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Binding adapters are responsible for making the appropriate framework calls to set values.
 * In this file we are creating binding adapters for a View.
 */
object ViewBinding {

    /**
     * Method to set the View visibility from the XML using the 'isVisible' attribute, passing
     * the data as a boolean.
     *
     * @param view
     * @param shouldBeGone
     */
    @JvmStatic
    @BindingAdapter("isVisible")
    fun bindGone(view: View, shouldBeGone: Boolean) {
        view.visibility = if (shouldBeGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

}