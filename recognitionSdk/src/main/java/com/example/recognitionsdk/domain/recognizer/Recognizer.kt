package com.example.recognitionsdk.domain.recognizer

import android.net.Uri
import com.example.recognitionsdk.domain.errorevent.ErrorEvent

/**
 * The core text recognizer working with
 * the ML-Kit [https://developers.google.com/ml-kit/vision/text-recognition/android]
 */
internal interface Recognizer {

    /**
     * Callback (listener) to receive recognized text blocks.
     *
     * @return callback (listener) to receive recognized text blocks.
     */
    var onSuccess: (List<String>) -> Unit

    /**
     * Callback (listener) to receive a report about errors. Not necessary.
     *
     * @return callback (listener) to receive a report about errors.
     */
    var onError: ((ErrorEvent) -> Unit)?

    /**
     * Starts text recognition.
     *
     * @param fileUri [Uri] of the picture file.
     * @param closeCallback closing call back invoked if everything is done or in case of failure
     */
    fun recognizeText(fileUri: Uri, closeCallback: (() -> Unit)?)
}