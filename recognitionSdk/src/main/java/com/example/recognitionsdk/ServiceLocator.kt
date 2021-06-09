package com.example.recognitionsdk

import com.example.recognitionsdk.domain.Recognizer
import com.example.recognitionsdk.domain.RecognizerImpl

// TODO Think about inetrface
internal object ServiceLocator {

    val recognizer: Recognizer by lazy { RecognizerImpl() }
}