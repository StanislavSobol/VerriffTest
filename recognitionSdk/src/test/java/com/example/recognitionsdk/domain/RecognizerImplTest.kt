package com.example.recognitionsdk.domain

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

/**
 * Unit-tests for [RecognizerImpl]
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(InputImage::class)
class RecognizerImplTest {

    @Mock
    private lateinit var appContext: Context

    @Mock
    private lateinit var fileUri: Uri

    @Mock
    private lateinit var imageFabric: () -> InputImage

    private lateinit var recognizerImpl: RecognizerImpl

    @Before
    fun setup() {
        PowerMockito.mockStatic(InputImage::class.java)
        recognizerImpl = RecognizerImpl()
    }

//    @Test
//    fun `recognizeText`() {
////        recognizerImpl.recognizeText(appContext, fileUri) { }
//        recognizerImpl.recognizeText({ }, imageFabric)
//
//        Assert.assertEquals("", "")
//    }

    private companion object {
        // const val FILE_URI = "FILE_URL"
    }
}