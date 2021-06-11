package com.example.recognitionsdk.presentation

import android.content.Context
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.recognitionsdk.R
import com.example.recognitionsdk.domain.Recognizer
import com.example.recognitionsdk.resourcemanager.ResourceManager
import com.example.recognitionsdk.servicelocator.ServiceLocator
import com.example.recognitionsdk.utils.errorevent.ErrorEvent
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.powermock.api.mockito.PowerMockito

/**
 * Unit-tests for [CameraViewModel]
 */
//@RunWith(PowerMockRunner::class)
//@PrepareForTest(CameraViewModel::class)
@RunWith(MockitoJUnitRunner::class)
class CameraViewModelTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var appContext: Context

    @Mock
    private lateinit var serviceLocator: ServiceLocator

    @Mock
    private lateinit var recognizer: Recognizer

    @Mock
    private lateinit var resourceManager: ResourceManager

    @Mock
    private lateinit var onError: (ErrorEvent) -> Unit

    @Mock
    private lateinit var closeObserver: Observer<OneShotEvent<Unit>>

    @Mock
    private lateinit var savedUri: Uri

    private lateinit var cameraViewModel: CameraViewModel

    @Before
    fun setup() {
        `when`(serviceLocator.recognizer).thenReturn(recognizer)
        `when`(serviceLocator.resourceManager).thenReturn(resourceManager)
        `when`(recognizer.onError).thenReturn(onError)
        `when`(resourceManager.getString(R.string.ex_permissions_not_granted)).thenReturn(PERMISSIONS_NOT_GRANTED)

        cameraViewModel = PowerMockito.spy(CameraViewModel(serviceLocator))
        //    cameraViewModel.closeEvent.observeForever { closeObserver }

//        PowerMockito.`when`(cameraViewModel.createErrorInfo(anyInt(), anyString())).thenReturn(Unit, Unit)
    }

    @After
    fun finish() {
        cameraViewModel.closeEvent.removeObserver(closeObserver)
    }

    @Test
    fun `imageSaved ok`() {
        cameraViewModel.imageSaved(appContext, savedUri)

        verify(recognizer).recognizeText(appContext, savedUri) { Unit }
//        { cameraViewModel.postCloseEvent() }
    }

    @Test
    fun `errorPermissionNorGrantedCaught ok`() {
        cameraViewModel.errorPermissionNorGrantedCaught()

//               verify(closeObserver).onChanged(any())

        verify(cameraViewModel).createErrorInfo(R.string.ex_permissions_not_granted)

//        verify(cameraViewModel.createErrorInfo(R.string.ex_permissions_not_granted), times(1))
//        verify(cameraViewModel).createErrorInfo(R.string.ex_permissions_not_granted)
//              verify(closeObserver, times(1)).onChanged(any())
    }

    private companion object {
        const val PERMISSIONS_NOT_GRANTED = "Permissions not granted by the user"
    }


}