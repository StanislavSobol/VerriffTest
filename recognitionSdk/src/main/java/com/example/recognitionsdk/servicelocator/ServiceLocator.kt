package com.example.recognitionsdk.servicelocator

import com.example.recognitionsdk.domain.Recognizer
import com.example.recognitionsdk.resourcemanager.ResourceManager

internal interface ServiceLocator {

    val recognizer: Recognizer

    val resourceManager: ResourceManager
}