package com.example.recognitionsdk.presentation

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recognitionsdk.R
import com.example.recognitionsdk.RecognitionSdk
import com.example.recognitionsdk.domain.Recognizer
import com.example.recognitionsdk.utils.ErrorInfo
import com.example.recognitionsdk.utils.OneShotEvent

internal class CameraViewModel : ViewModel() {

    private val _closeEvent = MutableLiveData<OneShotEvent<Unit>>()
    val closeEvent: LiveData<OneShotEvent<Unit>>
        get() = _closeEvent

    private val recognizer: Recognizer by lazy { RecognitionSdk.serviceLocator.recognizer }

    fun imageSaved(appContext: Context, savedUri: Uri) {
        recognizer.recognizeText(appContext, savedUri) { postCloseEvent() }
    }

    fun errorPermissionNorGrantedCaught() {
        ErrorInfo(R.string.ex_permissions_not_granted)
        postCloseEvent()
    }

    fun errorImageSaveFailureCaught(e: ImageCaptureException) {
        e.message?.let {
            ErrorInfo(
                R.string.ex_image_capture_error_with_message,
                e.message
            )
        } ?: run {
            ErrorInfo(R.string.ex_image_capture_error)
        }
        postCloseEvent()
    }

    fun errorInnerCameraErrorCaught(e: Exception) {
        e.message?.let {
            ErrorInfo(R.string.ex_inner_camera_with_message, e.message)
        } ?: run {
            ErrorInfo(R.string.ex_inner_camera)
        }
        postCloseEvent()
    }


    private fun postCloseEvent() {
        _closeEvent.postValue(OneShotEvent(Unit))
    }
}