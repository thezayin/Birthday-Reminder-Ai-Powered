package com.thezayin.presentation.components

import android.app.Activity
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thezayin.framework.extension.copyText
import com.thezayin.framework.extension.share
import com.thezayin.framework.extension.textToSpeech
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeResultBottomSheet(
    name: String,
    yearsCalculated: String,
    monthsCalculated: String,
    daysCalculated: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current as Activity
    var textToSpeech by remember { mutableStateOf<TextToSpeech?>(null) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true // Allows the sheet to skip intermediate positions
    )
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            coroutineScope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 18.sdp, topEnd = 18.sdp),
        containerColor = colorResource(id = R.color.card_background)
    ) {
        // Content of the Bottom Sheet
        Column(
            modifier = Modifier
                .padding(horizontal = 20.sdp)
                .padding(top = 20.sdp, bottom = 30.sdp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Close Button Row
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_cross_circle),
                    contentDescription = "Close",
                    modifier = Modifier
                        .size(18.sdp)
                        .clickable {
                            coroutineScope.launch {
                                sheetState.hide()
                                onDismiss()
                            }
                        }
                )
            }

            Spacer(modifier = Modifier.height(10.sdp))

            // Title
            Text(
                text = "Result",
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(id = R.color.text_color),
                fontSize = 14.ssp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.sdp))

            // Introductory Text
            Text(
                text = "Age of $name is:",
                fontFamily = FontFamily(Font(R.font.noto_sans_bold)),
                color = colorResource(id = R.color.text_color),
                fontSize = 12.ssp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.sdp))

            // Age Text
            Text(
                text = "$yearsCalculated years, $monthsCalculated months, $daysCalculated days",
                fontFamily = FontFamily(Font(R.font.noto_sans_medium)),
                color = colorResource(id = R.color.text_color),
                fontSize = 12.ssp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.sdp))

            // Actions Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.sdp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Copy Button
                Image(
                    painter = painterResource(id = R.drawable.ic_copy),
                    contentDescription = "Copy",
                    modifier = Modifier
                        .size(20.sdp)
                        .clickable {
                            context.copyText(
                                "$yearsCalculated years, $monthsCalculated months, $daysCalculated days"
                            )
                        }
                )

                // Play Button
                Button(
                    modifier = Modifier.size(55.sdp),
                    shape = RoundedCornerShape(100.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.transparent),
                    ),
                    onClick = {
                        textToSpeech = TextToSpeech(context) { status ->
                            if (status == TextToSpeech.SUCCESS) {
                                val result = textToSpeech?.setLanguage(Locale.US)
                                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Timber.tag("Voice Play").d("Language not supported")
                                } else {
                                    textToSpeech(
                                        "Age of $name is: $yearsCalculated years, $monthsCalculated months, $daysCalculated days",
                                        textToSpeech!!
                                    )
                                }
                            } else {
                                Timber.tag("Voice Play").d("Initialization Failed")
                            }
                        }
                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_play),
                        contentDescription = null,
                        modifier = Modifier.size(20.sdp)
                    )
                }

                // Share Button
                Image(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = "Share",
                    modifier = Modifier
                        .size(20.sdp)
                        .clickable {
                            context.share(
                                "$yearsCalculated years, $monthsCalculated months, $daysCalculated days"
                            )
                        }
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            textToSpeech?.shutdown()
        }
    }
}
