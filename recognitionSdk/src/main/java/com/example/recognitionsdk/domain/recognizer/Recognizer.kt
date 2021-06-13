package com.example.recognitionsdk.domain.recognizer

import android.content.Context
import android.net.Uri
import com.example.recognitionsdk.domain.errorevent.ErrorEvent

/**
 * The core text recognizer working with
 * the ML-Kit [https://developers.google.com/ml-kit/vision/text-recognition/android]
 */
internal interface Recognizer {

    /**
     * Callback (listener) to receive recognized text blocks.
     */
    var onSuccess: (List<String>) -> Unit

    /**
     * Callback (listener) to receive a report about errors. Not necessary.
     */
    var onError: ((ErrorEvent) -> Unit)?

    /**
     * Starts text recognition.
     */
    fun recognizeText(appContext: Context, fileUri: Uri, closeCallback: (() -> Unit)?)
}