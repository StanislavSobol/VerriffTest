package com.example.recognitionsdk.domain

import android.content.Context
import android.net.Uri
import com.example.recognitionsdk.R
import com.example.recognitionsdk.RecognitionSdkException
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizerOptions
import java.io.IOException

internal class RecognizerImpl : Recognizer {

    override lateinit var onSuccess: (List<String>) -> Unit

    override var onError: ((Exception) -> Unit)? = null

    override fun recognizeText(appContext: Context, fileUri: Uri) {
        val image: InputImage
        try {
            image = InputImage.fromFilePath(appContext, fileUri)
        } catch (e: IOException) {
            throw RecognitionSdkException(R.string.ex_bad_file)
        }

        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS).let {
            it.process(image)
                .addOnSuccessListener { visionText ->
                    val list = mutableListOf<String>()
                    list.addAll(visionText.textBlocks.map { textBlock -> textBlock.text })
                    onSuccess.invoke(list)
                }
                .addOnFailureListener { e ->
                    onError?.invoke(e)
                }
        }
    }
}