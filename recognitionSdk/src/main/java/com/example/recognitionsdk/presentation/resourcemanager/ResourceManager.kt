package com.example.recognitionsdk.presentation.resourcemanager

import androidx.annotation.StringRes

/**
 * Resource provider.
 */
internal interface ResourceManager {

    /**
     * Gets a single string from the resources.
     *
     * @param resId id of the string resource.
     */
    fun getString(@StringRes resId: Int): String

    /**
     * Builds a single string from the resources with a substring parameter.
     *
     * @param resId id of the string resource.
     * @param subStr substring parameter.
     */
    fun getString(@StringRes resId: Int, subStr: String): String
}