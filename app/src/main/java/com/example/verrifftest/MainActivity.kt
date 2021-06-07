package com.example.verrifftest

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException

// TODO ViewModel
class MainActivity : AppCompatActivity() {

  private var currentPhotoPath: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    findViewById<Button>(R.id.takePictureButton).setOnClickListener {
      dispatchTakePictureIntent()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
      val imageBitmap = data?.extras?.get("data") as? Bitmap
      imageBitmap?.let { findViewById<ImageView>(R.id.imageView).setImageBitmap(it) }

      val options = BitmapFactory.Options()
      options.inPreferredConfig = Bitmap.Config.ARGB_8888
      val bitmap = BitmapFactory.decodeFile(currentPhotoPath, options)
      findViewById<ImageView>(R.id.imageView).setImageBitmap(bitmap)
    }
  }

  private fun dispatchTakePictureIntent() {
    Intent(MediaStore.ACTION_IMAGE_CAPTURE).let { takePictureIntent ->
      // Ensure that there's a camera activity to handle the intent
      takePictureIntent.resolveActivity(packageManager)?.let {
        // Create the File where the photo should go
        val photoFile: File? = try {
          createImageFile()
        } catch (ex: IOException) {
          null
        }
        // Continue only if the File was successfully created
        photoFile?.let {
          val photoURI: Uri = FileProvider.getUriForFile(this, AUTHORITY, it)
          takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
          startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
      }
    }
  }

  @Throws(IOException::class)
  private fun createImageFile(): File {
    val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: throw IOException("Cannot create a file for storing a bitmap")
    return File.createTempFile(TMP_FILE_NAME, TMP_FILE_NAME_SUFFIX, storageDir).apply {
      currentPhotoPath = absolutePath
    }
  }

  companion object {
    const val REQUEST_IMAGE_CAPTURE = 1
    const val TMP_FILE_NAME = "image_to_transfer"
    const val TMP_FILE_NAME_SUFFIX = ".jpg"
    const val AUTHORITY = "com.example.android.fileprovider"
  }
}