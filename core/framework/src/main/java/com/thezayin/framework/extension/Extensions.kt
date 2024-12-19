package com.thezayin.framework.extension

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.speech.tts.TextToSpeech
import android.widget.Toast

fun Context.share(string: String) {
    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, string)
    startActivity(Intent.createChooser(shareIntent, "Share Via"))
}

fun Context.copyText(text: String) {
    val clipboard: ClipboardManager? =
        this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    val clip = ClipData.newPlainText("joke", text)
    clipboard?.setPrimaryClip(clip)
}

fun textToSpeech(text: String, textToSpeech: TextToSpeech) {
    textToSpeech.setSpeechRate(0.7f)
    if (textToSpeech.isSpeaking) {
        textToSpeech.stop()
    } else {
        textToSpeech.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            null
        )
    }
}


fun Context.sendMail() {
    val i = Intent(Intent.ACTION_SEND)
    i.setType("message/rfc822")
    i.putExtra(Intent.EXTRA_EMAIL, arrayOf("zainshahidbuttt@gmail.com"))
    i.putExtra(Intent.EXTRA_SUBJECT, "subject of email")
    i.putExtra(Intent.EXTRA_TEXT, "body of email")
    try {
        startActivity(Intent.createChooser(i, "Send mail..."))
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT)
            .show()
    }
}

fun Context.openLink(link: String) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(link)
    )
    this.startActivity(intent)
}