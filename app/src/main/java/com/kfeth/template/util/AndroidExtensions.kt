package com.kfeth.template.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kfeth.template.R

fun ImageView.setImageUrl(imageUrl: String?) {
    Glide.with(this)
        .load(imageUrl)
        .error(R.drawable.placeholder)
        .into(this)
}

fun Fragment.showSnackBarError(throwable: Throwable?) {
    view?.let {
        Snackbar.make(it, throwable?.message ?: "Unknown Error", Snackbar.LENGTH_LONG).show()
    }
}

fun ProgressBar.toggleVisibility(isLoading: Boolean) {
    visibility = when {
        isLoading -> View.VISIBLE
        else -> View.GONE
    }
}