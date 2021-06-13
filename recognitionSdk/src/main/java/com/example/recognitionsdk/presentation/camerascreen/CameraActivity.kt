package com.example.recognitionsdk.presentation.camerascreen

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recognitionsdk.R
import com.example.recognitionsdk.RecognitionSdk
import com.example.recognitionsdk.servicelocator.ServiceLocator
import com.example.recognitionsdk.servicelocator.ServiceLocatorImpl
import java.io.File

/**
 * Camera-screen for for taking a picture and text recognition
 */
internal class CameraActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(getServiceLocator())
        )
            .get(CameraViewModel::class.java)
    }

    // No need to store in the ViewModel
    private var imageCapture: ImageCapture? = null

    private val recognizeButton by lazy { findViewById<Button>(R.id.recognizeButton) }
    private val previewView by lazy { findViewById<PreviewView>(R.id.previewView) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.progressBar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        viewModel.closeEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let { finish() }
        })

        viewModel.photoFileCreatedLiveData.observe(this, Observer {
            continueTakingPhoto(it)
        })

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        recognizeButton.setOnClickListener {
            recognizeButton.isVisible = false
            progressBar.isVisible = true
            viewModel.recognizeButtonClicked()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                viewModel.errorPermissionNorGrantedCaught()
            }
        }
    }

    private fun getServiceLocator(): ServiceLocator {
        return try {
            RecognitionSdk.startManager.serviceLocator
        } catch (e: UninitializedPropertyAccessException) {
            // In case of Espresso tests
            ServiceLocatorImpl(this.applicationContext)
        }
    }

    private fun continueTakingPhoto(photoFile: File) {
        val imageCapture = imageCapture ?: return

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(e: ImageCaptureException) {
                    viewModel.errorImageSaveFailureCaught(e)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    viewModel.imageSaved(savedUri)
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
                viewModel.errorInnerCameraErrorCaught(e)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf("android.permission.CAMERA")
    }
}