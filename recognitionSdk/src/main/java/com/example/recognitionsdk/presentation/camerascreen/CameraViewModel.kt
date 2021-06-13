package com.example.recognitionsdk.presentation.camerascreen

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recognitionsdk.R
import com.example.recognitionsdk.servicelocator.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * ViewModel of the camera-screen.
 *
 * @property serviceLocator all main objects of the SDK.
 */
internal class CameraViewModel(private val serviceLocator: ServiceLocator) : ViewModel() {

    private val _closeEvent = MutableLiveData<OneShotEvent<Unit>>()
    val closeEvent: LiveData<OneShotEvent<Unit>>
        get() = _closeEvent

    private val _photoFileCreatedLiveData = MutableLiveData<File>()
    val photoFileCreatedLiveData: LiveData<File>
        get() = _photoFileCreatedLiveData

    /**
     * Event from the view: image saved.
     *
     * @param savedUri [Uri] of the saved file.
     */
    // TODO appContext ???
    fun imageSaved(appContext: Context, savedUri: Uri) {
        serviceLocator.recognizer.recognizeText(appContext, savedUri) {
            _closeEvent.postValue(
                OneShotEvent(
                    Unit
                )
            )
        }
    }

    /**
     * Error message received: permissions not granted by the user.
     */
    fun errorPermissionNorGrantedCaught() {
        serviceLocator.errorEventProducer.produce(R.string.err_permissions_not_granted)
        _closeEvent.postValue(OneShotEvent(Unit))
    }

    /**
     * Error message received: error while capturing an image.
     *
     * @param e caught inner [ImageCaptureException] from the recognition ML-Kit library.
     */
    fun errorImageSaveFailureCaught(e: ImageCaptureException) {
        val errorEventProducer = serviceLocator.errorEventProducer
        e.message?.let {
            errorEventProducer.produce(R.string.err_image_capture_error_with_message, e.message)
        } ?: run {
            errorEventProducer.produce(R.string.err_image_capture_error)
        }
        _closeEvent.postValue(OneShotEvent(Unit))
    }

    /**
     * Error message received: error while capturing an image.
     *      *
     * @param e caught inner [Exception] from the camera.
     */
    fun errorInnerCameraErrorCaught(e: Exception) {
        e.message?.let {
            serviceLocator.errorEventProducer.produce(R.string.err_inner_camera_with_message, e.message)
        } ?: run {
            serviceLocator.errorEventProducer.produce(R.string.err_inner_camera)
        }
        _closeEvent.postValue(OneShotEvent(Unit))
    }

    /**
     * Event from the view: the recognition button clicked.
     */
    fun recognizeButtonClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            val fileName: String = SimpleDateFormat(FILENAME_FORMAT, Locale.getDefault())
                .format(System.currentTimeMillis()) + "." + FILE_EXT
            val photoFile = File(serviceLocator.appContext.cacheDir, fileName)

            _photoFileCreatedLiveData.postValue(photoFile)
        }
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val FILE_EXT = "bmp"
    }
}