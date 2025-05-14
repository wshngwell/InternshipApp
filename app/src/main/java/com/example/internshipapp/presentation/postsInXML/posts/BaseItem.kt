package com.example.internshipapp.presentation.postsInXML.posts

import android.view.View

interface BaseItem {

    fun getItemViewType(): Int

    fun getItemsSame(): String

    fun onBindViewHolder(view: View)

    fun areItemsTheSame(): String = this.javaClass.name + getItemsSame()

    fun areContentsTheSame(): String = this.toString()
}