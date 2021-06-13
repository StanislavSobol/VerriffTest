package com.example.recognitionsdk

import android.content.Context
import com.example.recognitionsdk.RecognitionSdk.recognizeTextFromCamera
import com.example.recognitionsdk.RecognitionSdk.setOnErrorListener
import com.example.recognitionsdk.RecognitionSdk.setOnSuccessListener
import com.example.recognitionsdk.RecognitionSdk.withActivityContext
import com.example.recognitionsdk.domain.errorevent.ErrorEvent
import com.example.recognitionsdk.startmanager.StartManager
import com.example.recognitionsdk.startmanager.StartManagerImpl

/**
 * # The main entry-point of [RecognitionSdk].
 *
 * ## How to use
 *
 * 1. The SDK uses builder pattern.
 * 2. Place [RecognitionSdk] first.
 * 3. Place parent activity [Context] to start recognition screen. See [withActivityContext].
 * 4. Add the callback (listener) to receive recognized text blocks to the builder-chain. See [setOnSuccessListener].
 * 5. In order to receive an error log add [setOnErrorListener] to the builder-chain.
 * 6. The recognition screen starts by [recognizeTextFromCamera].
 *
 * ## Typical call chain

 * RecognitionSdk
 *
 *      .withActivityContext(this)
 *      .setOnSuccessListener {
 *        if (it.isEmpty()) {
 *           showError(...)
 *         } else {
 *            showTextBlocks(it)
 *          }
 *      }
 *      .setOnErrorListener {
 *         showError(it.message)
 *      }
 *      .recognizeTextFromCamera()
 */
object RecognitionSdk {

    internal val startManager: StartManager =
        StartManagerImpl()

    /**
     * Binds the parent activity [Context] in order to start recognition screen.
     *
     * @param activityContext the parent activity [Context]
     * @return this object to create a builder chain
     */
    fun withActivityContext(activityContext: Context): RecognitionSdk {
        startManager.withActivityContext(activityContext)
        return this
    }

    /**
     * Callback (listener) to receive recognized text blocks.
     *
     * @param onSuccess callback for the result
     * @return this object to create a builder chain
     */
    fun setOnSuccessListener(onSuccess: (List<String>) -> Unit): RecognitionSdk {
        startManager.setOnSuccessListener(onSuccess)
        return this
    }

    /**
     * Callback (listener) to receive a report about errors. Not necessary.
     *
     * @param onError callback for the result
     * @return this object to create a builder chain
     */
    fun setOnErrorListener(onError: (ErrorEvent) -> Unit): RecognitionSdk {
        startManager.setOnErrorListener(onError)
        return this
    }

    /**
     * Starts text recognition.
     * Before this call [withActivityContext] and [setOnSuccessListener] must be called.
     * [setOnErrorListener] should be called if the error report required.
     */
    fun recognizeTextFromCamera() {
        startManager.recognizeTextFromCamera()
    }
}