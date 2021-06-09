package com.example.recognitionsdk

import androidx.annotation.StringRes

// TODO check it on error callback
class RecognitionSdkException(
    @StringRes private val messageStringRes: Int,
    private val param: String? = null
) : RuntimeException() {

    init {
        RecognitionSdk.serviceLocator.recognizer.onError?.invoke(this)
    }

    override val message: String
        get() = getMessageString()

    private fun getMessageString(): String {
        val resourceManager = RecognitionSdk.serviceLocator.resourceManager
        return if (param.isNullOrBlank()) {
            resourceManager.getString(messageStringRes)
        } else {
            resourceManager.getString(messageStringRes, param)
        }
    }
}