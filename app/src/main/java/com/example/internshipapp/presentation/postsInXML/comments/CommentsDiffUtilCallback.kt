package com.example.internshipapp.presentation.postsInXML.comments

import androidx.recyclerview.widget.DiffUtil
import com.example.internshipapp.domain.entities.CommentEntity

class CommentsDiffUtilCallback : DiffUtil.ItemCallback<CommentEntity>() {
    override fun areItemsTheSame(oldItem: CommentEntity, newItem: CommentEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CommentEntity, newItem: CommentEntity): Boolean {
        return oldItem == newItem
    }
}