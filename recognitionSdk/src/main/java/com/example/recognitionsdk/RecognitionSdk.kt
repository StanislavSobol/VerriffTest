package com.example.recognitionsdk

import android.content.Context
import com.example.recognitionsdk.domain.errorevent.ErrorEvent
import com.example.recognitionsdk.startmanager.StartManager
import com.example.recognitionsdk.startmanager.StartManagerImpl

object RecognitionSdk {

    internal val startManager: StartManager =
        StartManagerImpl()

    fun withActivityContext(activityContext: Context): RecognitionSdk {
        startManager.withActivityContext(activityContext)
        return this
    }

    fun setOnSuccessListener(onSuccess: (List<String>) -> Unit): RecognitionSdk {
        startManager.setOnSuccessListener(onSuccess)
        return this

    }

    fun setOnErrorListener(onError: (ErrorEvent) -> Unit): RecognitionSdk {
        startManager.setOnErrorListener(onError)
        return this
    }

    fun recognizeTextFromCamera() {
        startManager.recognizeTextFromCamera()
    }
}