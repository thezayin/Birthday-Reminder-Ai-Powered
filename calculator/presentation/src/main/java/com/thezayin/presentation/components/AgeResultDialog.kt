package com.thezayin.presentation.components

import android.app.Activity
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import com.thezayin.framework.extension.functions.copyText
import com.thezayin.framework.extension.functions.share
import com.thezayin.framework.extension.functions.textToSpeech
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale

@Composable
fun AgeResultDialog(
    name: String,
    yearsCalculated: String,
    monthsCalculated: String,
    daysCalculated: String,
    showAd: Boolean,
    ad: @Composable () -> Unit,
    nativeAd: () -> Unit,
    onDismiss: () -> Unit,
    showDialog: (Boolean) -> Unit,
) {
    val context = LocalContext.current as Activity
    lateinit var textToSpeech: TextToSpeech
    val scope = rememberCoroutineScope()
    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                scope.launch {
                    while (this.isActive) {
                        nativeAd()
                        delay(20000L)
                    }
                }
            }

            else -> {}
        }
    }
    Dialog(onDismissRequest = { }) {
        Surface(
            shape = RoundedCornerShape(16.sdp),
            color = colorResource(id = R.color.card_background),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.padding(20.sdp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.sdp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Image(
                            contentDescription = "Close",
                            modifier = Modifier
                                .size(20.sdp)
                                .clickable {
                                    showDialog(false)
                                    onDismiss()
                                },
                            painter = painterResource(id = R.drawable.ic_cross_circle),
                        )
                    }

                    Spacer(modifier = Modifier.height(20.sdp))
                    Text(
                        text = "Result",
                        fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                        color = colorResource(id = R.color.text_color),
                        fontSize = 16.ssp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.sdp))
                    // Introductory Text
                    Text(
                        text = "Age of $name is:",
                        fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                        color = colorResource(id = R.color.text_color),
                        fontSize = 16.ssp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.sdp))
                    Text(
                        text = "$yearsCalculated years, $monthsCalculated months, $daysCalculated days",
                        fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
                        color = colorResource(id = R.color.text_color),
                        fontSize = 14.ssp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.sdp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.sdp, vertical = 10.sdp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            modifier = Modifier
                                .size(25.sdp)
                                .clickable {
                                    context.copyText(
                                        "$yearsCalculated years, $monthsCalculated months, $daysCalculated days"
                                    )
                                },
                            contentDescription = "Copy",
                            painter = painterResource(id = R.drawable.ic_copy),
                        )
                        Button(
                            modifier = Modifier.size(55.sdp),
                            shape = RoundedCornerShape(100.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.transparent),
                            ),
                            onClick = {
                                textToSpeech(
                                    "Age of $name is: $yearsCalculated years, $monthsCalculated months, $daysCalculated days",
                                    textToSpeech
                                )
                            }) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_play),
                                    contentDescription = null,
                                    modifier = Modifier.size(23.sdp)
                                )
                                textToSpeech = TextToSpeech(context) { status ->
                                    if (status == TextToSpeech.SUCCESS) {
                                        val result = textToSpeech.setLanguage(Locale.US)
                                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                            Timber
                                                .tag("Voice Play")
                                                .d("Language not supported")
                                        }
                                    } else {
                                        Timber
                                            .tag("Voice Play")
                                            .d("Initialization Failed")
                                    }
                                }
                            }
                        }
                        Image(
                            modifier = Modifier
                                .size(25.sdp)
                                .clickable {
                                    context.share(
                                        "$yearsCalculated years, $monthsCalculated months, $daysCalculated days"
                                    )
                                },
                            contentDescription = "Share",
                            painter = painterResource(id = R.drawable.ic_share),
                        )
                    }
                    if (showAd) {
                        ad()
                    }
                }
            }
        }
    }
}