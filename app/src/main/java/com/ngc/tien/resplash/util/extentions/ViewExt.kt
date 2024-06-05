package com.ngc.tien.resplash.util.extentions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.airbnb.lottie.LottieAnimationView
import com.ngc.tien.resplash.R

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

fun FragmentActivity.replaceFragment(
    fragment: Fragment,
    needAddToStack: Boolean = false
) {
    supportFragmentManager.commit {
        replace(R.id.fragmentContainer, fragment)
        setReorderingAllowed(true)
        if (needAddToStack) {
            addToBackStack(fragment::class.simpleName)
        }
    }
}