package com.example.recognitionsdk

import android.content.Context
import android.content.Intent
import com.example.recognitionsdk.presentation.CameraActivity
import java.lang.ref.WeakReference

// TODO Object
object RecognitionSdk2 {

    private lateinit var activityContext: WeakReference<Context>
    private lateinit var onSuccess: ((List<String>) -> Unit)

    private var onError: ((Exception) -> Unit)? = null

    fun withActivityContext(activityContext: Context): RecognitionSdk2 {
        return this.apply { this.activityContext = WeakReference(activityContext) }
    }

    fun setOnSuccessListener(onSuccess: (List<String>) -> Unit): RecognitionSdk2 {
        return this.apply { this.onSuccess = onSuccess }

    }

    // TODO Contact the excetption type
    fun setOnErrorListener(onError: (Exception) -> Unit): RecognitionSdk2 {
        return this.apply { this.onError = onError }
    }

    fun recognizeTextFromCamera() {
        val activity = activityContext.get()

        // TODO message consts
        activity ?: throw RecognitionSdkInnerException("NO CONTEXT")

        ServiceLocator.recognizer.onSuccess = onSuccess
        ServiceLocator.recognizer.onError = onError

        activity.startActivity(Intent(activity, CameraActivity::class.java))
    }
}