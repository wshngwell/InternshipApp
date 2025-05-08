package com.example.internshipapp.presentation.postsInXML

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> LifecycleOwner.collectFlow(
    flow: Flow<T>,
    invoke: suspend (T) -> Unit
) = when (this) {
    is Fragment -> viewLifecycleOwner.lifecycleScope
    is Activity -> lifecycleScope
    else -> throw Exception("unknown type")
}.launch {
    repeatOnLifecycle(Lifecycle.State.RESUMED) {
        flow.collect {
            invoke(it)
        }
    }
}