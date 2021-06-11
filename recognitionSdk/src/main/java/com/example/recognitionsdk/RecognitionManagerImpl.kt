package com.example.recognitionsdk

import android.content.Context
import android.content.Intent
import com.example.recognitionsdk.presentation.CameraActivity
import com.example.recognitionsdk.servicelocator.ServiceLocator
import com.example.recognitionsdk.servicelocator.ServiceLocatorImpl
import com.example.recognitionsdk.utils.RecognitionSdkException
import com.example.recognitionsdk.utils.errorevent.ErrorEvent
import java.lang.ref.WeakReference

internal class RecognitionManagerImpl : RecognitionManager {

    override val serviceLocator: ServiceLocator
        get() = _serviceLocator

    private lateinit var _serviceLocator: ServiceLocator
    private lateinit var activityContext: WeakReference<Context>
    private lateinit var onSuccess: ((List<String>) -> Unit)

    private var onError: ((ErrorEvent) -> Unit)? = null

    override fun withActivityContext(activityContext: Context) {
        this.activityContext = WeakReference(activityContext)
    }

    override fun setOnSuccessListener(onSuccess: (List<String>) -> Unit) {
        this.onSuccess = onSuccess
    }

    override fun setOnErrorListener(onError: (ErrorEvent) -> Unit) {
        this.onError = onError
    }

    override fun recognizeTextFromCamera() {
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
        private const val ACTIVITY_ERROR = "RecognitionSDK: the starting activity context must be specified first"
    }
}