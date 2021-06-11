package com.example.recognitionsdk

import android.content.Context
import com.example.recognitionsdk.servicelocator.ServiceLocator
import com.example.recognitionsdk.utils.errorevent.ErrorEvent

internal interface RecognitionManager {

    val serviceLocator: ServiceLocator

    fun withActivityContext(activityContext: Context)

    fun setOnSuccessListener(onSuccess: (List<String>) -> Unit)

    fun setOnErrorListener(onError: (ErrorEvent) -> Unit)

    fun recognizeTextFromCamera()
}