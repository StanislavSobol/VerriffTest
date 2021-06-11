package com.example.recognitionsdk.presentation.camerascreen

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recognitionsdk.R
import com.example.recognitionsdk.servicelocator.ServiceLocator

// TODO UNit Tests
internal class CameraViewModel(private val serviceLocator: ServiceLocator) : ViewModel() {

    private val _closeEvent = MutableLiveData<OneShotEvent<Unit>>()
    val closeEvent: LiveData<OneShotEvent<Unit>>
        get() = _closeEvent

    fun imageSaved(appContext: Context, savedUri: Uri) {
        serviceLocator.recognizer.recognizeText(appContext, savedUri) {
            _closeEvent.postValue(
                OneShotEvent(
                    Unit
                )
            )
        }
    }

    fun errorPermissionNorGrantedCaught() {
        serviceLocator.errorEventProducer.produce(R.string.ex_permissions_not_granted)
        _closeEvent.postValue(OneShotEvent(Unit))
    }

    fun errorImageSaveFailureCaught(e: ImageCaptureException) {
        val errorEventProducer = serviceLocator.errorEventProducer
        e.message?.let {
            errorEventProducer.produce(R.string.ex_image_capture_error_with_message, e.message)
        } ?: run {
            errorEventProducer.produce(R.string.ex_image_capture_error)
        }
        _closeEvent.postValue(OneShotEvent(Unit))
    }

    fun errorInnerCameraErrorCaught(e: Exception) {
        e.message?.let {
            serviceLocator.errorEventProducer.produce(R.string.ex_inner_camera_with_message, e.message)
        } ?: run {
            serviceLocator.errorEventProducer.produce(R.string.ex_inner_camera)
        }
        _closeEvent.postValue(OneShotEvent(Unit))
    }
}