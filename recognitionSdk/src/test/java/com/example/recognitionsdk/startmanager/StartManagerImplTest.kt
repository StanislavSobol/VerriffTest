package com.example.recognitionsdk.startmanager

import android.content.Context
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Unit tests for [StartManagerImpl]
 */
@RunWith(MockitoJUnitRunner::class)
class StartManagerImplTest {

    @Mock
    private lateinit var activityContext: Context

    @Mock
    private lateinit var appContext: Context

    private lateinit var startManagerImpl: StartManagerImpl

    @Before
    fun setup() {
        `when`(activityContext.applicationContext).thenReturn(appContext)

        startManagerImpl = StartManagerImpl()
    }

    @Test
    fun `full chain ok`() {
        startManagerImpl.withActivityContext(activityContext)
        startManagerImpl.setOnSuccessListener { }
        startManagerImpl.setOnErrorListener { }

        startManagerImpl.recognizeTextFromCamera()

        assertNotNull(startManagerImpl.serviceLocator)
        assertNotNull(startManagerImpl.activityContext)
        assertNotNull(startManagerImpl.activityContext.get())
        assertNotNull(startManagerImpl.onSuccess)
        assertNotNull(startManagerImpl.onError)
    }

    @Test
    fun `full chain without errorListener ok`() {
        startManagerImpl.withActivityContext(activityContext)
        startManagerImpl.setOnSuccessListener { }

        startManagerImpl.recognizeTextFromCamera()

        assertNotNull(startManagerImpl.serviceLocator)
        assertNotNull(startManagerImpl.activityContext)
        assertNotNull(startManagerImpl.activityContext.get())
        assertNotNull(startManagerImpl.onSuccess)
        assertNull(startManagerImpl.onError)
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun `full chain without successListener failure`() {
        startManagerImpl.withActivityContext(activityContext)

        startManagerImpl.recognizeTextFromCamera()
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun `full chain without activity  failure`() {
        startManagerImpl.setOnSuccessListener { }

        startManagerImpl.recognizeTextFromCamera()
    }
}