package com.example.recognitionsdk.domain.recognizer

import android.net.Uri
import com.example.recognitionsdk.R
import com.example.recognitionsdk.domain.errorevent.ErrorEvent
import com.example.recognitionsdk.servicelocator.ServiceLocator
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizerOptions
import java.io.IOException

/**
 * The main [Recognizer] implementation.
 *
 * @property serviceLocator all main objects of the SDK.
 */
internal class RecognizerImpl(private val serviceLocator: ServiceLocator) : Recognizer {

    override lateinit var onSuccess: (List<String>) -> Unit

    override var onError: ((ErrorEvent) -> Unit)? = null

    override fun recognizeText(fileUri: Uri, closeCallback: (() -> Unit)?) {
        val image: InputImage
        try {
            image = InputImage.fromFilePath(serviceLocator.appContext, fileUri)
        } catch (e: IOException) {
            serviceLocator.errorEventProducer.produce(R.string.err_bad_file)
            closeCallback?.invoke()
            return
        }

        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS).let {
            it.process(image)
                .addOnSuccessListener { visionText ->
                    val list = mutableListOf<String>()
                    list.addAll(visionText.textBlocks.map { textBlock -> textBlock.text })
                    onSuccess.invoke(list)
                    closeCallback?.invoke()
                }
                .addOnFailureListener { e ->
                    serviceLocator.errorEventProducer.produce(R.string.err_image_capture_error_with_message, e.message)
                    closeCallback?.invoke()
                }
        }
    }
}