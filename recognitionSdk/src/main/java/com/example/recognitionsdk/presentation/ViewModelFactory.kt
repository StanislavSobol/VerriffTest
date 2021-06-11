package com.example.recognitionsdk.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recognitionsdk.servicelocator.ServiceLocator

internal class ViewModelFactory(private val serviceLocator: ServiceLocator) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CameraViewModel(serviceLocator) as T
    }
}