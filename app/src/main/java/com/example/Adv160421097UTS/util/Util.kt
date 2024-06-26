package com.example.Adv160421097UTS.util

import android.widget.ImageView
import android.widget.ProgressBar
import com.example.Adv160421097UTS.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import android.view.View


fun ImageView.loadImageWithProgressBar(url: String?, progressBar: ProgressBar) {
    // Load image with Picasso
    Picasso.get()
        .load(url)
        .resize(400, 400)
        .centerCrop()
        .error(R.drawable.baseline_android_24)
        .into(this, object : Callback {
            override fun onSuccess() {
                progressBar.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
            }
        })
}