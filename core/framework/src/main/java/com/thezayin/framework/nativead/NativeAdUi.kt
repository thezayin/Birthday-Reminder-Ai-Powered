package com.thezayin.framework.nativead

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.children
import androidx.core.view.isInvisible
import androidx.databinding.ViewDataBinding
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.databinding.LayoutAdmobNativeButtonOutlineBinding
import com.thezayin.ads.databinding.LayoutAdmobNativeSmallBinding
import com.thezayin.framework.extension.functions.getActivity

sealed class GoogleNativeAdStyle {
    data object Small : GoogleNativeAdStyle()
    sealed class Button : GoogleNativeAdStyle() {
        data object Outline : Button()
        data object Fill : Button()
    }
}

@Composable
fun GoogleNativeAd(
    modifier: Modifier = Modifier,
    style: GoogleNativeAdStyle = GoogleNativeAdStyle.Small,
    nativeAd: NativeAd? = null,
    placeholder: @Composable () -> Unit = {}
) {
    val context = LocalContext.current
    val activity = context.getActivity()

    if (LocalInspectionMode.current) {
        placeholder()
        return
    }

    Box(
        modifier = modifier.clip(shape = RoundedCornerShape(8.dp))
    ) {
        if (activity != null && nativeAd != null) Box(Modifier) {
            when (style) {
                GoogleNativeAdStyle.Small -> GoogleNativeSmall.Render(nativeAd)
                GoogleNativeAdStyle.Button.Outline -> GoogleNativeButton.Outline.Render(nativeAd)
                GoogleNativeAdStyle.Button.Fill -> GoogleNativeSmall.Render(nativeAd)
            }
        } else {
            placeholder()
        }
    }
}

private sealed class GoogleNativeButton<B : ViewDataBinding> : GoogleNativeAdLayout<B>() {
    data object Outline : GoogleNativeButton<LayoutAdmobNativeButtonOutlineBinding>() {
        override fun inflate(inflater: LayoutInflater) =
            LayoutAdmobNativeButtonOutlineBinding.inflate(inflater)

        override fun LayoutAdmobNativeButtonOutlineBinding.bind(nativeAd: NativeAd) {
            nativeView.callToActionView = adCallToAction
            adCallToActionText.usingNullable(nativeAd.callToAction) { text = it }
            nativeView.iconView = adIcon.apply {
                usingNullable(nativeAd.icon) { setImageDrawable(it.drawable) }
            }
            nativeView.setNativeAd(nativeAd)
            nativeView.children.forEach { view ->
                if (view is FrameLayout) {
                    view.layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                }
            }
        }
    }
}

private data object GoogleNativeSmall : GoogleNativeAdLayout<LayoutAdmobNativeSmallBinding>() {
    override fun inflate(inflater: LayoutInflater) = LayoutAdmobNativeSmallBinding.inflate(inflater)
    override fun LayoutAdmobNativeSmallBinding.bind(nativeAd: NativeAd) {
        nativeView.headlineView = adHeadline.apply {
            text = nativeAd.headline
        }
        nativeView.callToActionView = btnCallToAction.apply {
            usingNullable(nativeAd.callToAction) { text = it }
        }
        nativeView.iconView = adIcon.apply {
            usingNullable(nativeAd.icon) { setImageDrawable(it.drawable) }
        }
        nativeView.setNativeAd(nativeAd)
    }
}

private sealed class GoogleNativeAdLayout<B : ViewDataBinding> {
    @Composable
    fun Render(instance: NativeAd) {
        var binding: B? by remember { mutableStateOf(null) }
        AndroidView(
            factory = { inflate(it.layoutInflater).apply { binding = this }.root },
            update = { binding?.bind(instance) }
        )
    }

    protected abstract fun inflate(inflater: LayoutInflater): B
    protected abstract fun B.bind(nativeAd: NativeAd)
}

private inline fun <V : View, T> V.usingNullable(parameter: T?, action: V.(T) -> Unit) {
    isInvisible = parameter == null
    if (!isInvisible) action(parameter!!)
}

val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

