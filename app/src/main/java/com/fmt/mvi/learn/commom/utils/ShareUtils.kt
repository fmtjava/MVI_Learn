package com.fmt.mvi.learn.commom.utils

import android.content.Context
import android.content.Intent

object ShareUtils {

    private const val TEXT_PLAIN = "text/plain"

    fun share(context: Context, title: String, content: String) {
        with(Intent()) {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, content)
            type = TEXT_PLAIN
            context.startActivity(Intent.createChooser(this, title))
        }
    }
}