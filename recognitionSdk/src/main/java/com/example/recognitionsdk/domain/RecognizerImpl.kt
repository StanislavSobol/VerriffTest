package com.example.recognitionsdk.domain

import android.content.Context
import android.net.Uri
import com.example.recognitionsdk.R
import com.example.recognitionsdk.servicelocator.ServiceLocator
import com.example.recognitionsdk.utils.errorevent.ErrorEvent
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizerOptions
import java.io.IOException

internal class RecognizerImpl(private val serviceLocator: ServiceLocator) : Recognizer {

    override lateinit var onSuccess: (List<String>) -> Unit

    override var onError: ((ErrorEvent) -> Unit)? = null

    override fun recognizeText(appContext: Context, fileUri: Uri, closeCallback: (() -> Unit)?) {
        val image: InputImage
        try {
            image = InputImage.fromFilePath(appContext, fileUri)
        } catch (e: IOException) {
            serviceLocator.errorEventProducer.produce(R.string.ex_bad_file)
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
                    serviceLocator.errorEventProducer.produce(R.string.ex_image_capture_error_with_message, e.message)
                    closeCallback?.invoke()
                }
        }
    }
//
//    @VisibleForTesting
////    internal fun recognizeText(appContext: Context, fileUri: Uri, image: InputImage, closeCallback: () -> Unit) {
//    internal fun recognizeText(
//        closeCallback: () -> Unit,
//        imageFabric: () -> InputImage
//    ) {
//        val image = imageFabric.invoke()
//
//        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS).let {
//            it.process(image)
//                .addOnSuccessListener { visionText ->
//                    val list = mutableListOf<String>()
//                    list.addAll(visionText.textBlocks.map { textBlock -> textBlock.text })
//                    onSuccess.invoke(list)
//                    closeCallback.invoke()
//                }
//                .addOnFailureListener { e ->
//                    onError?.invoke(ErrorInfo(R.string.ex_image_capture_error_with_message, e.message))
//                    closeCallback.invoke()
//                }
//        }
//    }
}