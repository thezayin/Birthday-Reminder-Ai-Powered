package com.thezayin.framework.extension.ads.native

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.databinding.LayoutAdmobNativeMaxBinding
import kotlinx.coroutines.CoroutineScope

class NativeInterstitialAds(
    context: Context,
    private val ad: NativeAd,
    private val scope: CoroutineScope
) : Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
    private var binding: LayoutAdmobNativeMaxBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            LayoutAdmobNativeMaxBinding.inflate(layoutInflater).apply {
                binding = this
                ad.show(scope, binding) {
                    dismiss()
                }
            }.root
        )
        setCancelable(false)
    }
}