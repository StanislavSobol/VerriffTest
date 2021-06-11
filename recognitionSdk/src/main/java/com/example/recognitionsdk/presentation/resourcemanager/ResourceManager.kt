package com.example.recognitionsdk.presentation.resourcemanager

import androidx.annotation.StringRes

internal interface ResourceManager {

    fun getString(@StringRes resId: Int): String

    fun getString(@StringRes resId: Int, subStr: String): String
}