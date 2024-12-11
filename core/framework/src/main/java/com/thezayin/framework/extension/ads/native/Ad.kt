package com.thezayin.framework.extension.ads.native

import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.databinding.LayoutAdmobNativeMaxBinding
import kotlinx.coroutines.CoroutineScope

fun NativeAd.show(
    coroutineScope: CoroutineScope,
    binding: LayoutAdmobNativeMaxBinding?,
    onDismiss: () -> Unit,
) {
    binding.show(coroutineScope, this, onDismiss)
}