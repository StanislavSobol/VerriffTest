package com.example.recognitionsdk.servicelocator

import android.content.Context
import com.example.recognitionsdk.domain.errorevent.ErrorEventProducer
import com.example.recognitionsdk.domain.recognizer.Recognizer
import com.example.recognitionsdk.presentation.resourcemanager.ResourceManager

internal interface ServiceLocator {

    val appContext: Context

    val recognizer: Recognizer

    val resourceManager: ResourceManager

    val errorEventProducer: ErrorEventProducer
}