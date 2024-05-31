package com.ngc.tien.resplash.modules.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.ngc.tien.resplash.R
import com.ngc.tien.resplash.util.ViewUtils

class AspectRatioImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        drawable?.let {
            val width = measuredWidth
            val height =
                (measuredWidth / drawable.intrinsicWidth.toFloat()) *
                        drawable.intrinsicHeight
            setMeasuredDimension(
                width,
                height.toInt().coerceAtLeast(minimumHeight)
            )
        }
    }

    fun setAspectRatioAndColorForThumbnail(photoWidth: Int, photoHeight: Int, photoColor: String) {
        val photoWrapperWidth =
            ViewUtils.getScreenWidth() - 2 * context.resources.getDimensionPixelSize(
                R.dimen.dimen_16dp
            )
        val params = layoutParams
        params.width = photoWrapperWidth
        params.height = ((photoWrapperWidth.toFloat() / photoWidth) *
                photoHeight).toInt().coerceAtLeast(minimumHeight)
        layoutParams = params
        setBackgroundColor(Color.parseColor(photoColor))
    }
}