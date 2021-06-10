package com.example.recognitionsdk.domain

import android.content.Context
import android.net.Uri
import com.example.recognitionsdk.utils.ErrorInfo

internal interface Recognizer {

    var onSuccess: (List<String>) -> Unit

    var onError: ((ErrorInfo) -> Unit)?

    fun recognizeText(appContext: Context, fileUri: Uri, closeCallback: () -> Unit)
}