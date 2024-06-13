package com.ngc.tien.resplash.util.extentions

import android.view.View
import com.airbnb.lottie.LottieAnimationView

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun LottieAnimationView.pauseAndGone() {
    gone()
    pauseAnimation()
}

fun LottieAnimationView.playAndShow() {
    visible()
    playAnimation()
}

fun View.transparent(transparent: Boolean) {
    alpha = if (transparent) 0f else 1f
}