package com.example.recognitionsdk.domain.errorevent

import androidx.annotation.StringRes

/**
 * Producer of error event log.
 */
interface ErrorEventProducer {

    /**
     * The main  error event log producing method.
     * @param messageStringRes string resource of the error message.
     * @param param additional string parameter to the main error message.
     */
    fun produce(@StringRes messageStringRes: Int, param: String? = null)
}