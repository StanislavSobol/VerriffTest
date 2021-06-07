package com.example.recognitionsdk

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizerOptions
import java.io.IOException

// TODO Object
object RecognitionSdk {

    private var context: Context? = null

    /**
     * Activity context
     */
    fun start(context: Context) {
        this.context = context
        context.startActivity(Intent(context, RecognitionActivity::class.java))

    }


    fun recognizeText(bitmap: Bitmap): String? {
        val image = InputImage.fromBitmap(bitmap, 0)
        return null
    }

    fun recognizeText(appContext: Context, fileUri: Uri): String? {
        val image: InputImage
        try {
            image = InputImage.fromFilePath(appContext, fileUri)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                // Task completed successfully
                // ...
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
            }

        return null
    }
}