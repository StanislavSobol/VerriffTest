package com.example.recognitionsdk.domain.recognizer

import android.content.Context
import android.net.Uri
import com.example.recognitionsdk.domain.errorevent.ErrorEvent

internal interface Recognizer {

    var onSuccess: (List<String>) -> Unit

    var onError: ((ErrorEvent) -> Unit)?

    fun recognizeText(appContext: Context, fileUri: Uri, closeCallback: (() -> Unit)?)
}