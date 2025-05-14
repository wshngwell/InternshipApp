package com.example.internshipapp.presentation.postsInXML.posts

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BaseRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    init {
        if (layoutManager == null) {
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDetachedFromWindow() {
        adapter = null
        super.onDetachedFromWindow()
    }
}