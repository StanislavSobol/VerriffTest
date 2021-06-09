package com.example.recognitionsdk.presentation

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.recognitionsdk.ServiceLocator
import com.example.recognitionsdk.domain.Recognizer

internal class CameraViewModel : ViewModel() {

    private val recognizer: Recognizer by lazy { ServiceLocator.recognizer }

    fun imageSaved(appContext: Context, savedUri: Uri) {
        recognizer.recognizeText(appContext, savedUri)
    }
}