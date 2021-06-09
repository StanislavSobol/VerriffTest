package com.example.recognitionsdk.presentation

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recognitionsdk.R
import com.example.recognitionsdk.utils.RecognitionSdkException
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

internal class CameraActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(CameraViewModel::class.java) }

    // No need to store in the ViewModel
    private var imageCapture: ImageCapture? = null

    private val takePhotoButton by lazy { findViewById<Button>(R.id.takePhotoButton) }
    private val previewView by lazy { findViewById<PreviewView>(R.id.previewView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        viewModel.closeEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let { finish() }
        })

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        takePhotoButton.setOnClickListener { takePhoto() }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                throw RecognitionSdkException(R.string.ex_permissions_not_granted)
            }
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            cacheDir,
            SimpleDateFormat(FILENAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis()) + ".bmp"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(e: ImageCaptureException) {
                    e.message?.let {
                        throw RecognitionSdkException(
                            R.string.ex_image_capture_error_with_message,
                            e.message
                        )
                    } ?: run {
                        throw RecognitionSdkException(R.string.ex_image_capture_error)
                    }
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    viewModel.imageSaved(this@CameraActivity.applicationContext, savedUri)
                }
            })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .apply { setSurfaceProvider(previewView.surfaceProvider) }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                e.message?.let {
                    throw RecognitionSdkException(R.string.ex_intercepted_with_message, e.message)
                } ?: run {
                    throw RecognitionSdkException(R.string.ex_intercepted)
                }
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf("android.permission.CAMERA")
    }
}