package com.example.recognitionsdk.presentation

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.recognitionsdk.RecognitionSdk
import com.example.recognitionsdk.domain.Recognizer

// TODO Check the lifecycle
internal class CameraViewModel : ViewModel() {

    private val recognizer: Recognizer by lazy { RecognitionSdk.serviceLocator.recognizer }

    fun imageSaved(appContext: Context, savedUri: Uri) {
        recognizer.recognizeText(appContext, savedUri)
    }
}