package com.test.moviebrowser.ui

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.moviebrowser.R

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: AppCompatImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(imageView)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_image_not_found)
                .placeholder(R.drawable.ic_image_not_found)
                .into(imageView)
        }
    }
}