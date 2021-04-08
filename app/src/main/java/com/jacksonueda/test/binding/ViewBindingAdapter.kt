package com.jacksonueda.test.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Binding adapters are responsible for making the appropriate framework calls to set values.
 * In this file we are creating binding adapters for a View.
 */
object ViewBindingAdapter {

    /**
     * Method to set the View visibility from the XML using the 'isVisible' attribute, passing
     * the data as a boolean.
     *
     * @param view
     * @param visible
     */
    @JvmStatic
    @BindingAdapter("isVisible")
    fun bindVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.loadImage(url: String?) {
        if (url.isNullOrEmpty()) return
        Glide.with(this).load(url).into(this)
    }

}