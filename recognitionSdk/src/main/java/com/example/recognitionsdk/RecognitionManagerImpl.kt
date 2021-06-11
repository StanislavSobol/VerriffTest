package com.example.recognitionsdk

import android.content.Context
import android.content.Intent
import com.example.recognitionsdk.presentation.CameraActivity
import com.example.recognitionsdk.servicelocator.ServiceLocator
import com.example.recognitionsdk.servicelocator.ServiceLocatorImpl
import com.example.recognitionsdk.utils.ErrorEvent
import com.example.recognitionsdk.utils.RecognitionSdkException
import java.lang.ref.WeakReference

internal class RecognitionManagerImpl : RecognitionManager {

    override val serviceLocator: ServiceLocator
        get() = _serviceLocator

    private lateinit var _serviceLocator: ServiceLocator
    private lateinit var activityContext: WeakReference<Context>
    private lateinit var onSuccess: ((List<String>) -> Unit)

    private var onError: ((ErrorEvent) -> Unit)? = null

    fun withActivityContext(activityContext: Context): RecognitionManager {
        return this.apply { this.activityContext = WeakReference(activityContext) }
    }

    fun setOnSuccessListener(onSuccess: (List<String>) -> Unit): RecognitionManager {
        return this.apply { this.onSuccess = onSuccess }
    }

    fun setOnErrorListener(onError: (ErrorEvent) -> Unit): RecognitionManager {
        return this.apply { this.onError = onError }
    }

    fun recognizeTextFromCamera() {
        val activity = activityContext.get()
        activity ?: throw RecognitionSdkException(ACTIVITY_ERROR)

        _serviceLocator = ServiceLocatorImpl(
            activity.applicationContext
        ).apply {
            recognizer.onSuccess = onSuccess
            recognizer.onError = onError
        }

        activity.startActivity(Intent(activity, CameraActivity::class.java))
    }

    companion object {
        private const val ACTIVITY_ERROR = "The activity context must be specified first"
    }

}