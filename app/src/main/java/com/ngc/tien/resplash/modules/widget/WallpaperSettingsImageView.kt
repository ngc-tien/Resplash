package com.ngc.tien.resplash.modules.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.abs

class WallpaperSettingsImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var bitmap: Bitmap? = null
    private var zoomedWidth = 0
    private val matrix = Matrix()
    private val touchPoint = PointF()
    private var isLoaded = false
    private var maxTranX = 0f
    override fun onDraw(canvas: Canvas) {
        bitmap?.let { bitmap ->
            if (!isLoaded) {
                val scale = height.toFloat() / bitmap.height
                zoomedWidth = (bitmap.width * scale).toInt()
                maxTranX = (width - bitmap.width * scale) / 2
                matrix.setScale(scale, scale)
                matrix.postTranslate(maxTranX, 0f)
                isLoaded = true
            }
            canvas.drawBitmap(bitmap, matrix, null)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchPoint.set(event.x, 0f)
            MotionEvent.ACTION_MOVE -> {
                val delta = event.x - touchPoint.x
                val values = FloatArray(9)
                val newMatrix = Matrix(matrix)
                newMatrix.postTranslate(delta, 0f)
                newMatrix.getValues(values)
                val currentTranslationX = values[Matrix.MTRANS_X]
                if (delta > 0 && currentTranslationX < 0) {
                    matrix.postTranslate(delta, 0f)
                    touchPoint.set(event.x, 0f)
                } else if (delta < 0f && abs(currentTranslationX) < abs(maxTranX * 2)) {
                    matrix.postTranslate(delta, 0f)
                    touchPoint.set(event.x, 0f)
                }
                invalidate()
            }
        }
        return true
    }

    fun setBitmap(bitmap: Bitmap?) {
        this.bitmap = bitmap
        invalidate()
    }

    fun getCurrentBitmap(): Bitmap? {
        bitmap?.let { originalBitmap ->
            val transformedBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(transformedBitmap)
            canvas.drawBitmap(originalBitmap, matrix, null)
            return transformedBitmap
        }
        return null
    }
}