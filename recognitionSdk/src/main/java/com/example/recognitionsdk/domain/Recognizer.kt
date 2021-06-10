package com.example.recognitionsdk.domain

import android.content.Context
import android.net.Uri
import com.example.recognitionsdk.utils.RecognitionSdkException

internal interface Recognizer {

    var onSuccess: (List<String>) -> Unit

    var onError: ((RecognitionSdkException) -> Unit)?

    fun recognizeText(appContext: Context, fileUri: Uri)
}