package com.example.recognitionsdk.resourcemanager

import android.content.Context
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Test for [ResourceManager]
 */
@RunWith(MockitoJUnitRunner::class)
class ResourceManagerTest {

    @Mock
    private lateinit var appContext: Context

    private lateinit var resourceManagerImpl: ResourceManagerImpl

    @Before
    fun setup() {
        `when`(appContext.getString(CORRECT_STRING_RES_ID)).thenReturn(CORRECT_STRING)
        `when`(appContext.getString(CORRECT_STRING_WITH_PARAM_RES_ID, PARAM)).thenReturn(CORRECT_STRING_WITH_PARAM)

        resourceManagerImpl = ResourceManagerImpl(appContext)
    }

    @Test
    fun `getString ok`() {
        assertEquals(CORRECT_STRING, resourceManagerImpl.getString(CORRECT_STRING_RES_ID))
    }

    @Test
    fun `getString with param ok`() {
        assertEquals(CORRECT_STRING_WITH_PARAM, resourceManagerImpl.getString(CORRECT_STRING_WITH_PARAM_RES_ID, PARAM))
    }

    @Test(expected = NullPointerException::class)
    fun `getString bad`() {
        resourceManagerImpl.getString(INCORRECT_STRING_RES_ID)
    }

    private companion object {
        const val CORRECT_STRING_RES_ID = 777
        const val CORRECT_STRING_WITH_PARAM_RES_ID = 888
        const val PARAM = "888"
        const val INCORRECT_STRING_RES_ID = 999
        const val CORRECT_STRING = "Test string"
        const val CORRECT_STRING_WITH_PARAM = "$CORRECT_STRING$PARAM"
    }
}