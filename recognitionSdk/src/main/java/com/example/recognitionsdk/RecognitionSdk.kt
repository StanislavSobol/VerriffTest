package com.example.recognitionsdk

import android.content.Context
import android.content.Intent
import com.example.recognitionsdk.presentation.CameraActivity
import com.example.recognitionsdk.servicelocator.ServiceLocator
import com.example.recognitionsdk.servicelocator.ServiceLocatorImpl
import com.example.recognitionsdk.utils.RecognitionSdkException
import java.lang.ref.WeakReference

// TODO Object
object RecognitionSdk {

    internal lateinit var serviceLocator: ServiceLocator

    private lateinit var activityContext: WeakReference<Context>
    private lateinit var onSuccess: ((List<String>) -> Unit)

    private var onError: ((Exception) -> Unit)? = null

    fun withActivityContext(activityContext: Context): RecognitionSdk {
        return this.apply { this.activityContext = WeakReference(activityContext) }
    }

    fun setOnSuccessListener(onSuccess: (List<String>) -> Unit): RecognitionSdk {
        return this.apply { this.onSuccess = onSuccess }

    }

    fun setOnErrorListener(onError: (Exception) -> Unit): RecognitionSdk {
        return this.apply { this.onError = onError }
    }

    fun recognizeTextFromCamera() {
        val activity = activityContext.get()
        activity ?: throw RecognitionSdkException(R.string.ex_no_activity_context)

        serviceLocator = ServiceLocatorImpl(
            activity.applicationContext
        ).apply {
            recognizer.onSuccess = onSuccess
            recognizer.onError = onError
        }

        activity.startActivity(Intent(activity, CameraActivity::class.java))
    }
}