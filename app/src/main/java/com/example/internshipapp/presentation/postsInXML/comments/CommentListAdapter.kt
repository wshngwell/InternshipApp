package com.example.internshipapp.presentation.postsInXML.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.internshipapp.databinding.CommentCardBinding
import com.example.internshipapp.domain.entities.CommentEntity

class CommentListAdapter :
    ListAdapter<CommentEntity, CommentsViewHolder>(CommentsDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = CommentCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val comment = currentList[position]
        with(holder.binding) {
            commentTitle.text = comment.name
            commentEmail.text = comment.email
            commentMainText.text = comment.body
        }
    }
}