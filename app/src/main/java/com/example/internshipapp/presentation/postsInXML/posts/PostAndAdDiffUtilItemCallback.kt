package com.example.internshipapp.presentation.postsInXML.posts

import androidx.recyclerview.widget.DiffUtil

class PostAndAdDiffUtilItemCallback : DiffUtil.ItemCallback<IPostsAndAdUiModels>() {
    override fun areItemsTheSame(
        oldItem: IPostsAndAdUiModels,
        newItem: IPostsAndAdUiModels
    ): Boolean {
        return if (oldItem is IPostsAndAdUiModels.AdsUiModel
            && newItem is IPostsAndAdUiModels.AdsUiModel
        ) {
            return oldItem.ad.id == newItem.ad.id

        } else if (oldItem is IPostsAndAdUiModels.PostsUiModel
            && newItem is IPostsAndAdUiModels.PostsUiModel
        ) {
            return oldItem.post.id == newItem.post.id
        } else false
    }

    override fun areContentsTheSame(
        oldItem: IPostsAndAdUiModels,
        newItem: IPostsAndAdUiModels
    ): Boolean {
        return if (oldItem is IPostsAndAdUiModels.AdsUiModel
            && newItem is IPostsAndAdUiModels.AdsUiModel
        ) {
            return oldItem.ad == newItem.ad

        } else if (oldItem is IPostsAndAdUiModels.PostsUiModel
            && newItem is IPostsAndAdUiModels.PostsUiModel
        ) {
            return oldItem.post == newItem.post
        } else false
    }
}