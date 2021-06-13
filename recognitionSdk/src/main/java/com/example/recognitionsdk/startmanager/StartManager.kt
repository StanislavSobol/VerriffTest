package com.example.recognitionsdk.startmanager

import android.content.Context
import com.example.recognitionsdk.RecognitionSdk
import com.example.recognitionsdk.domain.errorevent.ErrorEvent
import com.example.recognitionsdk.servicelocator.ServiceLocator


/**
 * An SDK starting manager. Passes all the information from the [RecognitionSdk] and creates all
 * needed objects and links
 */
internal interface StartManager {

    /**
     * Provides all main objects of the SDK.
     */
    val serviceLocator: ServiceLocator

    /**
     * Binds the parent activity [Context] in order to start recognition screen.
     *
     * @param activityContext the parent activity [Context]
     */
    fun withActivityContext(activityContext: Context)

    /**
     * Callback (listener) to receive recognized text blocks.
     *
     * @param onSuccess callback for the result
     */
    fun setOnSuccessListener(onSuccess: (List<String>) -> Unit)

    /**
     * Callback (listener) to receive a report about errors. Not necessary.
     *
     * @param onError callback for the result
     */
    fun setOnErrorListener(onError: (ErrorEvent) -> Unit)

    /**
     * Starts text recognition.
     */
    fun recognizeTextFromCamera()
}