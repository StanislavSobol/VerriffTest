package com.example.recognitionsdk.resourcemanager

import androidx.annotation.StringRes

internal interface ResourceManager {

    fun getString(@StringRes resId: Int): String
}