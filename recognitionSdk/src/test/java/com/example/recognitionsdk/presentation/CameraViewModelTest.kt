package com.example.recognitionsdk.presentation

import android.content.Context
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.recognitionsdk.R
import com.example.recognitionsdk.RecognitionSdk
import com.example.recognitionsdk.domain.Recognizer
import com.example.recognitionsdk.servicelocator.ServiceLocator
import com.example.recognitionsdk.utils.ErrorEventProducer
import com.example.recognitionsdk.utils.OneShotEvent
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule


/**
 * Unit-tests for [CameraViewModel]
 */
//@RunWith(MockitoJUnitRunner::class)
@RunWith(PowerMockRunner::class)
@PrepareForTest(ErrorEventProducer::class, RecognitionSdk::class, ServiceLocator::class)
class CameraViewModelTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var appContext: Context

    @Mock
    private lateinit var savedUri: Uri

//    @Mock
//    private lateinit var closeCallback: () -> Unit

    @Mock
    private lateinit var recognizer: Recognizer

    @Mock
    private lateinit var serviceLocator: ServiceLocator

    @Mock
    private lateinit var closeObserver: Observer<OneShotEvent<Unit>>

    private lateinit var cameraViewModel: CameraViewModel

    @Before
    fun setup() {
        PowerMockito.mockStatic(ErrorEventProducer::class.java)
        PowerMockito.mockStatic(RecognitionSdk::class.java)
        PowerMockito.mockStatic(ServiceLocator::class.java)

        //   PowerMockito.`when`(RecognitionSdk.serviceLocator).thenReturn(serviceLocator)


//        cameraViewModel = CameraViewModel(recognizer)
        cameraViewModel = CameraViewModel(recognizer)

        //       `when`(cameraViewModel.createErrorInfo(anyInt(),anyString())).thenReturn(Unit)
        //   `when`(cameraViewModel.postCloseEvent()).thenReturn(Unit)

        //  cameraViewModel.closeEvent.observeForever(closeObserver)
    }

    @After
    fun finish() {
        cameraViewModel.closeEvent.removeObserver(closeObserver)
    }

    @Test
    fun `imageSaved`() {
        cameraViewModel.imageSaved(appContext, savedUri)

        verify(recognizer).recognizeText(appContext, savedUri) { Unit }
//        { cameraViewModel.postCloseEvent() }
    }

    @Test
    fun `errorPermissionNorGrantedCaught ok`() {
        cameraViewModel.errorPermissionNorGrantedCaught()

        verify(cameraViewModel).createErrorInfo(R.string.ex_permissions_not_granted)
        //      verify(closeObserver, times(1)).onChanged(any())
    }


}