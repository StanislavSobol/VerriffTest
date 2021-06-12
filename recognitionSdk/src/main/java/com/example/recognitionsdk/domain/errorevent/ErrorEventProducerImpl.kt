package com.example.recognitionsdk.domain.errorevent

import androidx.annotation.VisibleForTesting
import com.example.recognitionsdk.servicelocator.ServiceLocator

internal class ErrorEventProducerImpl(private val serviceLocator: ServiceLocator) : ErrorEventProducer {

    override fun produce(messageStringRes: Int, param: String?) {
        serviceLocator.recognizer.onError?.invoke(
            ErrorEvent(
                getMessageString(
                    messageStringRes = messageStringRes,
                    param = param
                )
            )
        )
    }

    @VisibleForTesting
    internal fun getMessageString(messageStringRes: Int, param: String?): String {
        val resourceManager = serviceLocator.resourceManager
        return if (param.isNullOrBlank()) {
            resourceManager.getString(messageStringRes)
        } else {
            resourceManager.getString(messageStringRes, param)
        }
    }

}

