package com.example.recognitionsdk.servicelocator

import com.example.recognitionsdk.domain.Recognizer
import com.example.recognitionsdk.resourcemanager.ResourceManager
import com.example.recognitionsdk.utils.errorevent.ErrorEventProducer

internal interface ServiceLocator {

    val recognizer: Recognizer

    val resourceManager: ResourceManager

    val errorEventProducer: ErrorEventProducer
}