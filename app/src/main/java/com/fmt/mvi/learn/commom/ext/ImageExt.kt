package com.fmt.mvi.learn.commom.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

@BindingAdapter(value = ["url", "isCircle"], requireAll = false)
fun ImageView.loadUrl(url: String, isCircle: Boolean) {
    load(url) {
        crossfade(true)
        if (isCircle) {
            transformations(CircleCropTransformation())
        }
    }
}