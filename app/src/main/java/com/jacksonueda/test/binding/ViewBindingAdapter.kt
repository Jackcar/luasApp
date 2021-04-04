package com.jacksonueda.test.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Binding adapters are responsible for making the appropriate framework calls to set values.
 * In this file we are creating binding adapters for a View.
 */
object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.loadImage(url: String?) {
        if (url.isNullOrEmpty()) return
        Glide.with(this).load(url).into(this)
    }

}