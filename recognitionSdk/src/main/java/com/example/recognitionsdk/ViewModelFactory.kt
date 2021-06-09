package com.example.recognitionsdk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recognitionsdk.presentation.CameraViewModel

@Deprecated("Not used")
class ViewModelFactory() : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CameraViewModel() as T
    }
}