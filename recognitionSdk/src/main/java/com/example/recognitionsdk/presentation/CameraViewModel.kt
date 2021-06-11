package com.example.recognitionsdk.presentation

import android.content.Context
import android.net.Uri
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recognitionsdk.R
import com.example.recognitionsdk.servicelocator.ServiceLocator
import com.example.recognitionsdk.utils.ErrorEventProducer
import com.example.recognitionsdk.utils.OneShotEvent

// TODO UNit Tests
internal open class CameraViewModel(private val serviceLocator: ServiceLocator) : ViewModel() {

    private val _closeEvent = MutableLiveData<OneShotEvent<Unit>>()
    val closeEvent: LiveData<OneShotEvent<Unit>>
        get() = _closeEvent

    fun imageSaved(appContext: Context, savedUri: Uri) {
        serviceLocator.recognizer.recognizeText(appContext, savedUri) { postCloseEvent() }
    }

    fun errorPermissionNorGrantedCaught() {
        createErrorInfo(R.string.ex_permissions_not_granted)
        postCloseEvent()
    }

    fun errorImageSaveFailureCaught(e: ImageCaptureException) {
        e.message?.let {
            ErrorEventProducer(
                serviceLocator,
                R.string.ex_image_capture_error_with_message,
                e.message
            )
        } ?: run {
            ErrorEventProducer(serviceLocator, R.string.ex_image_capture_error)
        }
        postCloseEvent()
    }

    fun errorInnerCameraErrorCaught(e: Exception) {
        e.message?.let {
            createErrorInfo(R.string.ex_inner_camera_with_message, e.message)
        } ?: run {
            createErrorInfo(R.string.ex_inner_camera)
        }
        postCloseEvent()
    }

    @VisibleForTesting
    internal fun postCloseEvent() {
        _closeEvent.postValue(OneShotEvent(Unit))
    }

    @VisibleForTesting
    internal fun createErrorInfo(@StringRes messageStringRes: Int, param: String? = null) {
        ErrorEventProducer(serviceLocator, messageStringRes, param)
    }
}