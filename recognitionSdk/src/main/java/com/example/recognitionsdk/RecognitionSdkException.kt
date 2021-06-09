package com.example.recognitionsdk

import androidx.annotation.StringRes

class RecognitionSdkException(
    @StringRes private val messageStringRes: Int
) : RuntimeException() {

    override val message: String?
        get() = RecognitionSdk.serviceLocator.resourceManager.getString(messageStringRes)
}