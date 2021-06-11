package com.example.recognitionsdk.presentation.resourcemanager

import android.content.Context
import androidx.annotation.StringRes

internal class ResourceManagerImpl(private val appContext: Context) :
    ResourceManager {

    override fun getString(@StringRes resId: Int) = appContext.getString(resId)

    override fun getString(@StringRes resId: Int, subStr: String) = appContext.getString(resId, subStr)
}