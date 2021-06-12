package com.example.recognitionsdk.servicelocator

import android.content.Context
import com.example.recognitionsdk.domain.errorevent.ErrorEventProducer
import com.example.recognitionsdk.domain.errorevent.ErrorEventProducerImpl
import com.example.recognitionsdk.domain.recognizer.Recognizer
import com.example.recognitionsdk.domain.recognizer.RecognizerImpl
import com.example.recognitionsdk.presentation.resourcemanager.ResourceManager
import com.example.recognitionsdk.presentation.resourcemanager.ResourceManagerImpl

internal class ServiceLocatorImpl(override val appContext: Context) : ServiceLocator {

    override val recognizer: Recognizer by lazy { RecognizerImpl(this) }

    override val resourceManager: ResourceManager by lazy { ResourceManagerImpl(appContext) }

    override val errorEventProducer: ErrorEventProducer by lazy { ErrorEventProducerImpl(this) }
}