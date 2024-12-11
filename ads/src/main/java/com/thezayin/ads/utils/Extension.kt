package com.thezayin.ads.utils

import android.content.Context
import android.webkit.WebView
import android.widget.Toast

fun isWebViewAvailable(context: Context): Boolean {
    return try {
        WebView(context).isHardwareAccelerated
        true
    } catch (e: Throwable) {
        false
    }
}
