package com.serjlaren.sloom.services

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourcesService @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun getString(@StringRes id: Int) = context.getString(id)

    fun getString(@StringRes id: Int, vararg args: Any) = context.getString(id, args)
}