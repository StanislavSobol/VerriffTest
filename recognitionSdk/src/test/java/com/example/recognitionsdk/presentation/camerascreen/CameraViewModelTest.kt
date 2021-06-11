package com.example.recognitionsdk.presentation.camerascreen

import android.content.Context
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.Observer
import com.example.recognitionsdk.R
import com.example.recognitionsdk.domain.errorevent.ErrorEventProducer
import com.example.recognitionsdk.domain.recognizer.Recognizer
import com.example.recognitionsdk.servicelocator.ServiceLocator
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Unit-tests for [CameraViewModel]
 *
 * About nulls and matchers [https://stackoverflow.com/questions/59230041/argumentmatchers-any-must-not-be-null]
 */
@RunWith(MockitoJUnitRunner::class)
class CameraViewModelTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var appContext: Context

    @Mock
    private lateinit var closeObserver: Observer<OneShotEvent<Unit>>

    @Mock
    private lateinit var savedUri: Uri

    @Mock
    private lateinit var serviceLocator: ServiceLocator

    @Mock
    private lateinit var recognizer: Recognizer

    @Mock
    private lateinit var errorEventProducer: ErrorEventProducer

    @Mock
    private lateinit var imageCaptureException: ImageCaptureException

    private lateinit var cameraViewModel: CameraViewModel

    @Before
    fun setup() {
        `when`(serviceLocator.recognizer).thenReturn(recognizer)
        `when`(serviceLocator.errorEventProducer).thenReturn(errorEventProducer)

        cameraViewModel =
            CameraViewModel(serviceLocator)
        cameraViewModel.closeEvent.observeForever(closeObserver)
    }

    @After
    fun finish() {
        cameraViewModel.closeEvent.removeObserver(closeObserver)
    }

    @Test
    fun `imageSaved ok`() {
        cameraViewModel.imageSaved(appContext, savedUri)
        verify(serviceLocator.recognizer).recognizeText(eq(appContext), eq(savedUri), any())
        verifyNoMoreInteractions(closeObserver)
    }

    @Test
    fun `errorPermissionNorGrantedCaught ok`() {
        cameraViewModel.errorPermissionNorGrantedCaught()
        verify(serviceLocator.errorEventProducer).produce(R.string.ex_permissions_not_granted, null)
        verify(closeObserver).onChanged(any())
    }

    @Test
    fun `errorImageSaveFailureCaught without message ok`() {
        cameraViewModel.errorImageSaveFailureCaught(imageCaptureException)

        verify(errorEventProducer).produce(R.string.ex_image_capture_error)
        verify(closeObserver).onChanged(any())
    }

    @Test
    fun `errorImageSaveFailureCaught with message ok`() {
        `when`(imageCaptureException.message).thenReturn(SAVE_FILE_FAILURE_MESSAGE)

        cameraViewModel.errorImageSaveFailureCaught(imageCaptureException)

        verify(errorEventProducer).produce(
            R.string.ex_image_capture_error_with_message,
            SAVE_FILE_FAILURE_MESSAGE
        )
        verify(closeObserver).onChanged(any())
    }

    @Test
    fun `errorInnerCameraErrorCaught without message ok`() {
        cameraViewModel.errorInnerCameraErrorCaught(imageCaptureException)

        verify(errorEventProducer).produce(R.string.ex_inner_camera)
        verify(closeObserver).onChanged(any())
    }

    @Test
    fun `errorInnerCameraErrorCaught with message ok`() {
        `when`(imageCaptureException.message).thenReturn(INNER_CAMERA_FAILURE_MESSAGE)

        cameraViewModel.errorInnerCameraErrorCaught(imageCaptureException)

        verify(errorEventProducer).produce(
            R.string.ex_inner_camera_with_message,
            INNER_CAMERA_FAILURE_MESSAGE
        )
        verify(closeObserver).onChanged(any())
    }

    private companion object {
        const val SAVE_FILE_FAILURE_MESSAGE = "SAVE_FILE_FAILURE_MESSAGE"
        const val INNER_CAMERA_FAILURE_MESSAGE = "INNER_CAMERA_FAILURE_MESSAGE"
    }
}