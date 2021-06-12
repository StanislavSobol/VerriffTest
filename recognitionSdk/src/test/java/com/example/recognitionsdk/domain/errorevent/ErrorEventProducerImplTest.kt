package com.example.recognitionsdk.domain.errorevent

import androidx.annotation.StringRes
import com.example.recognitionsdk.R
import com.example.recognitionsdk.domain.recognizer.Recognizer
import com.example.recognitionsdk.presentation.resourcemanager.ResourceManager
import com.example.recognitionsdk.servicelocator.ServiceLocator
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Unit-tests for [ErrorEventProducerImpl]
 */
@RunWith(MockitoJUnitRunner::class)
class ErrorEventProducerImplTest {

    @Mock
    private lateinit var serviceLocator: ServiceLocator

    @Mock
    private lateinit var recognizer: Recognizer

    @Mock
    private lateinit var onError: ((ErrorEvent) -> Unit)

    @Mock
    private lateinit var resourceManager: ResourceManager

    private lateinit var errorEventProducerImpl: ErrorEventProducerImpl

    @Before
    fun setup() {
        `when`(serviceLocator.recognizer).thenReturn(recognizer)
        `when`(serviceLocator.resourceManager).thenReturn(resourceManager)
        `when`(resourceManager.getString(TEST_STRING_RES)).thenReturn(TEST_STRING)
        `when`(resourceManager.getString(TEST_STRING_RES, PARAM)).thenReturn(TEST_STRING_WITH_PARAM)

        errorEventProducerImpl = ErrorEventProducerImpl(serviceLocator)
    }

    @Test
    fun `produce onError is not null`() {
        `when`(recognizer.onError).thenReturn(onError)

        errorEventProducerImpl.produce(R.string.ex_permissions_not_granted)

        assertNotNull(recognizer.onError)
        val expected = errorEventProducerImpl.getMessageString(TEST_STRING_RES, null)
        verify(onError).invoke(ErrorEvent(expected))
    }

    @Test
    fun `produce onError is not null with string param`() {
        `when`(recognizer.onError).thenReturn(onError)

        errorEventProducerImpl.produce(R.string.ex_permissions_not_granted, PARAM)

        assertNotNull(recognizer.onError)
        val expected = errorEventProducerImpl.getMessageString(TEST_STRING_RES, PARAM)
        verify(onError).invoke(ErrorEvent(expected))
    }

    @Test
    fun `produce onError is null`() {
        `when`(recognizer.onError).thenReturn(null)

        errorEventProducerImpl.produce(R.string.ex_permissions_not_granted)

        assertNull(recognizer.onError)
        verifyNoMoreInteractions(onError)
    }

    private companion object {
        @StringRes
        val TEST_STRING_RES = R.string.ex_permissions_not_granted

        const val TEST_STRING = "Permissions not granted by the user"
        const val TEST_STRING_WITH_PARAM = "Permissions not granted by the user with param:"
        const val PARAM = "PARAM"
    }
}