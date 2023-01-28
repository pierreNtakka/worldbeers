package com.ditta.worldbeers.ui.util

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun ImageView.loadImage(
    url: String?,
    @DrawableRes placeholderId: Int,
    @DrawableRes errorPlaceholderId: Int
) {
    Glide.with(this).load(url).skipMemoryCache(true)
        .placeholder(placeholderId).error(errorPlaceholderId)
        .into(this)
}
