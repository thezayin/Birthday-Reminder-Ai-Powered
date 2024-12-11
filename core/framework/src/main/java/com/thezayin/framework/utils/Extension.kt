package com.thezayin.framework.utils

import android.graphics.Bitmap
import android.text.format.DateFormat
import android.view.View
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.Date

fun takeScreenShot(view: View, dir: File): File? {
    try {
        view.isDrawingCacheEnabled = true
        val bitmap: Bitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false
        val dateText = DateFormat.format("yyyy-MM-dd_hh:mm:ss", Date())
        val imageFile = File("$dir/result-$dateText.jpeg")
        FileOutputStream(imageFile).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos)
            fos.flush()
        }
        return imageFile
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}