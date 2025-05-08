package com.example.internshipapp.presentation.postsInXML.posts

import androidx.recyclerview.widget.DiffUtil
import com.example.internshipapp.domain.entities.PostEntity

class PostDiffUtilItemCallback : DiffUtil.ItemCallback<PostEntity>() {
    override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
        return oldItem == newItem
    }
}