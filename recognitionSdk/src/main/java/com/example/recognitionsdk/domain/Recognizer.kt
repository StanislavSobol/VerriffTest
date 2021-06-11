package com.example.recognitionsdk.domain

import android.content.Context
import android.net.Uri
import com.example.recognitionsdk.utils.errorevent.ErrorEvent

internal interface Recognizer {

    var onSuccess: (List<String>) -> Unit

    var onError: ((ErrorEvent) -> Unit)?

    fun recognizeText(appContext: Context, fileUri: Uri, closeCallback: (() -> Unit)?)
}