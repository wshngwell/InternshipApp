package com.example.internshipapp.presentation.postsInXML.posts

import com.example.internshipapp.domain.additionalEntitues.AdEntity
import com.example.internshipapp.domain.entities.PostEntity

sealed interface IPostsAndAdUiModels {
    data class PostsUiModel(val post: PostEntity) : IPostsAndAdUiModels
    data class AdsUiModel(val ad: AdEntity) : IPostsAndAdUiModels
}