package com.example.recognitionsdk.domain.errorevent

import androidx.annotation.StringRes

interface ErrorEventProducer {

    fun produce(@StringRes messageStringRes: Int, param: String? = null)
}