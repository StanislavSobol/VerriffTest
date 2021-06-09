package com.example.recognitionsdk.domain

import android.content.Context
import android.net.Uri

internal interface Recognizer {

    var onSuccess: (List<String>) -> Unit

    var onError: ((Exception) -> Unit)?

    fun recognizeText(appContext: Context, fileUri: Uri)
}