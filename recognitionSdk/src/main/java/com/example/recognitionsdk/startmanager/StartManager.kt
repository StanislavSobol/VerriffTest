package com.example.recognitionsdk.startmanager

import android.content.Context
import com.example.recognitionsdk.domain.errorevent.ErrorEvent
import com.example.recognitionsdk.servicelocator.ServiceLocator

internal interface StartManager {

    val serviceLocator: ServiceLocator

    fun withActivityContext(activityContext: Context)

    fun setOnSuccessListener(onSuccess: (List<String>) -> Unit)

    fun setOnErrorListener(onError: (ErrorEvent) -> Unit)

    fun recognizeTextFromCamera()
}