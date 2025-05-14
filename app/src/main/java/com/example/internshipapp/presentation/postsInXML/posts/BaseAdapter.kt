package com.example.internshipapp.presentation.postsInXML.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class BaseAdapter : ListAdapter<BaseItem, ViewHolder>(BaseDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return object : ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
        ) {}
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].getItemViewType()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList[position].onBindViewHolder(holder.itemView)
    }
}