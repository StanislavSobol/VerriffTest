package com.example.recognitionsdk.servicelocator

import android.content.Context
import com.example.recognitionsdk.domain.errorevent.ErrorEventProducer
import com.example.recognitionsdk.domain.recognizer.Recognizer
import com.example.recognitionsdk.presentation.resourcemanager.ResourceManager

/**
 * Provides all main objects of the SDK.
 */
internal interface ServiceLocator {

    /**
     * Application [Context]
     */
    val appContext: Context

    /**
     * The core text recognizer working with
     * the ML-Kit [https://developers.google.com/ml-kit/vision/text-recognition/android]
     */
    val recognizer: Recognizer

    /**
     * The resource provider.
     */
    val resourceManager: ResourceManager

    /**
     * Producer of error event log.
     */
    val errorEventProducer: ErrorEventProducer
}