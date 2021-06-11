package com.example.recognitionsdk

import android.content.Context
import com.example.recognitionsdk.utils.ErrorEvent

// TODO Object
object RecognitionSdk {

    // TODO interface type
    internal val recognitionManager: RecognitionManagerImpl = RecognitionManagerImpl()

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