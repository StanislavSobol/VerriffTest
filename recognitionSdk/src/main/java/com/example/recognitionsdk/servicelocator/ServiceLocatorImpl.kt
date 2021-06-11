package com.example.recognitionsdk.servicelocator

import android.content.Context
import com.example.recognitionsdk.domain.Recognizer
import com.example.recognitionsdk.domain.RecognizerImpl
import com.example.recognitionsdk.resourcemanager.ResourceManager
import com.example.recognitionsdk.resourcemanager.ResourceManagerImpl

internal class ServiceLocatorImpl(appContext: Context) : ServiceLocator {

    override val recognizer: Recognizer by lazy { RecognizerImpl(this) }

    override val resourceManager: ResourceManager by lazy { ResourceManagerImpl(appContext) }
}