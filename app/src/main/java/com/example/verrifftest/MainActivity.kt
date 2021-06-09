package com.example.verrifftest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.recognitionsdk.RecognitionSdk2

// TODO ViewModel
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.takePictureButton).setOnClickListener {
//            RecognitionSdk.start(this) {
//                it.forEach { item -> Log.d("SSS", "Received from the SDK: $item") }
//            }


            RecognitionSdk2
                .withActivityContext(this)
                .setOnSuccessListener {
                    it.forEach { item -> Log.d("SSS", "Received from the SDK 2: $item") }
                }
                .setOnErrorListener {
                    Log.d("SSS", "The error from the SDK 2 : $it")
                }
                .recognizeTextFromCamera()
        }
    }
}