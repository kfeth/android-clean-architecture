package com.kfeth.androidcleanarchitecture.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kfeth.androidcleanarchitecture.R

fun ImageView.setImageUrl(imageUrl: String?) {
    Glide.with(this)
        .load(imageUrl)
        .error(R.drawable.placeholder)
        .into(this)
}