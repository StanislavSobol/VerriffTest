package com.example.recognitionsdk

import android.content.Context
import com.example.recognitionsdk.domain.errorevent.ErrorEvent

object RecognitionSdk {

    internal val recognitionManager: RecognitionManager = RecognitionManagerImpl()

    fun withActivityContext(activityContext: Context): RecognitionSdk {
        recognitionManager.withActivityContext(activityContext)
        return this
    }

    fun setOnSuccessListener(onSuccess: (List<String>) -> Unit): RecognitionSdk {
        recognitionManager.setOnSuccessListener(onSuccess)
        return this

    }

    fun setOnErrorListener(onError: (ErrorEvent) -> Unit): RecognitionSdk {
        recognitionManager.setOnErrorListener(onError)
        return this
    }

    fun recognizeTextFromCamera() {
        recognitionManager.recognizeTextFromCamera()
    }
}