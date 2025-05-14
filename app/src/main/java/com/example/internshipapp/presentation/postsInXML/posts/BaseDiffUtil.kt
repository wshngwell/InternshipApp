package com.example.internshipapp.presentation.postsInXML.posts

import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtil() : DiffUtil.ItemCallback<BaseItem>() {
    override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem.areItemsTheSame() == newItem.areItemsTheSame()
    }

    override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem.areContentsTheSame() == newItem.areContentsTheSame()
    }
}