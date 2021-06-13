package com.example.verrifftest

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.recognitionsdk.RecognitionSdk

class MainActivity : AppCompatActivity() {

    private val outputTextView by lazy { findViewById<TextView>(R.id.outputTextView) }
    private val swipeRefreshLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout) }
    private val retryButton by lazy { findViewById<Button>(R.id.retryButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: recognizeText()

        swipeRefreshLayout.setOnRefreshListener { recognizeText() }
        retryButton.setOnClickListener { recognizeText() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TEXT_BLOCKS_BUNDLE_KEY, outputTextView.text.toString())
        outState.putBoolean(RETRY_BUTTON_VISIBLE_BUNDLE_KEY, retryButton.isVisible)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        outputTextView.text = savedInstanceState.getString(TEXT_BLOCKS_BUNDLE_KEY, "")
        retryButton.isVisible = savedInstanceState.getBoolean(RETRY_BUTTON_VISIBLE_BUNDLE_KEY, false)
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun recognizeText() {
        outputTextView.text = ""

        RecognitionSdk
            .withActivityContext(this)
            .setOnSuccessListener {
                if (it.isEmpty()) {
                    showError(getString(R.string.no_data))
                } else {
                    showTextBlocks(it)
                }
            }
            .setOnErrorListener {
                showError(it.message)
            }
            .recognizeTextFromCamera()
    }

    private fun showTextBlocks(textBlocks: List<String>) {
        outputTextView.setTextColor(Color.BLACK)
        outputTextView.text = textBlocks.map { item -> "$item\n" }.toString()
        retryButton.isVisible = false
        swipeRefreshLayout.isRefreshing = false
    }

    private fun showError(message: String) {
        outputTextView.setTextColor(Color.RED)
        outputTextView.text = getString(R.string.error, message)
        retryButton.isVisible = true
        swipeRefreshLayout.isRefreshing = false
    }

    companion object {
        const val TEXT_BLOCKS_BUNDLE_KEY = "TEXT_BLOCKS_BUNDLE_KEY"
        const val RETRY_BUTTON_VISIBLE_BUNDLE_KEY = "RETRY_BUTTON_VISIBLE_BUNDLE_KEY"
    }
}