package com.example.recognitionsdk

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import com.example.recognitionsdk.presentation.CameraActivity
import com.google.mlkit.vision.common.InputImage

// TODO Object
object RecognitionSdk {

    private var context: Context? = null

    /**
     * Activity context
     */
    fun start(context: Context, successTextRecognitionCallback: (List<String>) -> Unit) {
        this.context = context
        ServiceLocator.recognizer.onSuccess = successTextRecognitionCallback
        context.startActivity(Intent(context, CameraActivity::class.java))
    }


    // TODO Maybe ok for tests
    fun recognizeText(bitmap: Bitmap): String? {
        val image = InputImage.fromBitmap(bitmap, 0)
        return null
    }
}