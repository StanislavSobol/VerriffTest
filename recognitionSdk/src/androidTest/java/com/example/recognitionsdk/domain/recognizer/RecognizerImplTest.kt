package com.example.recognitionsdk.domain.recognizer

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.recognitionsdk.servicelocator.ServiceLocatorImpl
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


/**
 * Tests for [RecognizerImpl]
 */
@RunWith(AndroidJUnit4::class)
class RecognizerImplTest {

    private lateinit var appContext: Context

    private lateinit var recognizerImpl: RecognizerImpl

    @Before
    fun setup() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        recognizerImpl = RecognizerImpl(ServiceLocatorImpl(appContext))
    }

    @Test
    fun recognizeText() {
        val assetManager: AssetManager = appContext.assets

        val assetsInputStream: InputStream = assetManager.open(SPECIMEN_ASSET_NAME)
        val bitmap = BitmapFactory.decodeStream(assetsInputStream)
        assetsInputStream.close()

        val cachedFile = File(appContext.cacheDir, CACHED_FILE_NAME)

        val cachedFileOutputStream = FileOutputStream(cachedFile.absolutePath)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, cachedFileOutputStream)
        cachedFileOutputStream.flush()
        cachedFileOutputStream.close()

        recognizerImpl.onSuccess = {
            assertNotNull(it)
            Assert.assertArrayEquals(expectedList.toTypedArray(), it.toTypedArray())
        }

        recognizerImpl.recognizeText(
            fileUri = Uri.fromFile(cachedFile),
            closeCallback = null
        )

        // The recognition processes synchronously hence we are waiting
        Thread.sleep(PROCESS_DELAY_MILLIS)
    }

    private companion object {
        const val SPECIMEN_ASSET_NAME = "specimen.JPG"
        const val CACHED_FILE_NAME = "cached_specimen.JPG"
        const val PROCESS_DELAY_MILLIS = 3000L

        val expectedList = listOf(
            "BAI234567",
            "ELAMISLUBA",
            "NIMI\nMÄNNIK\nMARI-LIIS",
            "EN",
            "KEHTIV KUN\n19.05.2015",
            "VÄLJAANDMISE KOHT JA KuUPAEV\nPPA, 19.05.2010",
            "LOA LIK\nTAHTAJALINE ELAMISsLUBA",
            "PKUSED\nKEAPORARY RESIDENCE PERMIT\nKONI/UNTIL 19.05.2015",
            "KASUTAJA ALLKIRI",
            "RESIDENCE PERMIT"
        )
    }
}