package com.example.surfin.util

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImageMain(imageView: ImageView, mainImage: String?) {
    Log.i("PhotoBinding","$mainImage ")
    mainImage?.let {
        var imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .into(imageView)
    }
}
