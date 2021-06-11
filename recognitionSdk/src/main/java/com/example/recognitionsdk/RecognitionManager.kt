package com.example.recognitionsdk

import android.content.Context
import com.example.recognitionsdk.domain.errorevent.ErrorEvent
import com.example.recognitionsdk.servicelocator.ServiceLocator

internal interface RecognitionManager {

    val serviceLocator: ServiceLocator

    fun withActivityContext(activityContext: Context)

    fun setOnSuccessListener(onSuccess: (List<String>) -> Unit)

    fun setOnErrorListener(onError: (ErrorEvent) -> Unit)

    fun recognizeTextFromCamera()
}