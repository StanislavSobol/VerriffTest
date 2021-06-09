package com.example.recognitionsdk.presentation

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recognitionsdk.RecognitionSdk
import com.example.recognitionsdk.domain.Recognizer
import com.example.recognitionsdk.utils.OneShotEvent

// TODO Check the lifecycle
internal class CameraViewModel : ViewModel() {

    private val _closeEvent = MutableLiveData<OneShotEvent<Unit>>()
    val closeEvent: LiveData<OneShotEvent<Unit>>
        get() = _closeEvent

    private val recognizer: Recognizer by lazy { RecognitionSdk.serviceLocator.recognizer }

    fun imageSaved(appContext: Context, savedUri: Uri) {
        recognizer.recognizeText(appContext, savedUri)
        _closeEvent.value = OneShotEvent(Unit)
    }
}