package com.example.recognitionsdk.utils.errorevent

import androidx.annotation.StringRes
import com.example.recognitionsdk.servicelocator.ServiceLocator

internal class ErrorEventProducer(
    private val serviceLocator: ServiceLocator,
    @StringRes private val messageStringRes: Int,
    private val param: String? = null
) {

    init {
        serviceLocator.recognizer.onError?.invoke(ErrorEvent(getMessageString()))
    }

    private fun getMessageString(): String {
        val resourceManager = serviceLocator.resourceManager
        return if (param.isNullOrBlank()) {
            resourceManager.getString(messageStringRes)
        } else {
            resourceManager.getString(messageStringRes, param)
        }
    }
}

