package com.ngc.tien.resplash.util.extentions

import android.icu.text.CompactDecimalFormat
import java.util.Locale

fun Int.formatNumber(): String {
    val formattedNumber = CompactDecimalFormat.getInstance(Locale.getDefault(), CompactDecimalFormat.CompactStyle.SHORT)
    return formattedNumber.format(this)
}