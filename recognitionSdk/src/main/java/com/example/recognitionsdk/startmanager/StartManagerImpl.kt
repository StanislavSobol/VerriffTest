package com.example.recognitionsdk.startmanager

import android.content.Context
import android.content.Intent
import androidx.annotation.VisibleForTesting
import com.example.recognitionsdk.domain.errorevent.ErrorEvent
import com.example.recognitionsdk.presentation.camerascreen.CameraActivity
import com.example.recognitionsdk.servicelocator.ServiceLocator
import com.example.recognitionsdk.servicelocator.ServiceLocatorImpl
import com.example.recognitionsdk.utils.RecognitionSdkBuilderException
import java.lang.ref.WeakReference

/**
 * The main [StartManager] implementation.
 */
internal class StartManagerImpl : StartManager {

    private lateinit var _serviceLocator: ServiceLocator
    override val serviceLocator: ServiceLocator
        get() = _serviceLocator

    @VisibleForTesting
    internal lateinit var activityContext: WeakReference<Context>

    @VisibleForTesting
    internal lateinit var onSuccess: ((List<String>) -> Unit)

    @VisibleForTesting
    internal var onError: ((ErrorEvent) -> Unit)? = null

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
        activity ?: throw RecognitionSdkBuilderException(ACTIVITY_ERROR)

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