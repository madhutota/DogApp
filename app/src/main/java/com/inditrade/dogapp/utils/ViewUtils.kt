package com.inditrade.dogapp.utils

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.inditrade.dogapp.R
import kotlin.random.Random

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.showSnakeBar(message: String) {
    val snack = Snackbar
        .make(this, message, Snackbar.LENGTH_LONG)
    snack.setTextColor(ContextCompat.getColor(this.context, R.color.white))
    snack.setBackgroundTint(ContextCompat.getColor(this.context, R.color.black))
    snack.show()
}

fun View.showSnakeBarDefault(message: String) {
    val snack = Snackbar
        .make(this, message, Snackbar.LENGTH_LONG)
    snack.show()
}


fun Int.Companion.randomColor(): Int {
    return Color.argb(
        255,
        Random.nextInt(256),
        Random.nextInt(256),
        Random.nextInt(256)
    )
}


