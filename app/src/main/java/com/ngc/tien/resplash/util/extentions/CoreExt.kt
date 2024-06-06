package com.ngc.tien.resplash.util.extentions

import android.app.Activity
import android.content.Context
import android.icu.text.CompactDecimalFormat
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ShareCompat
import java.util.Locale


fun Int.formatNumber(): String {
    val formattedNumber = CompactDecimalFormat.getInstance(Locale.getDefault(), CompactDecimalFormat.CompactStyle.SHORT)
    return formattedNumber.format(this)
}

fun String.shareUrl(context: Context) {
    ShareCompat
        .IntentBuilder(context)
        .setType("text/plain")
        .setChooserTitle("Share with: ")
        .setText(this)
        .startChooser()
}

fun Activity.launchUrl(url: String) {
    val intent: CustomTabsIntent = CustomTabsIntent.Builder()
        .build()
    intent.launchUrl(this, Uri.parse(url))
}
