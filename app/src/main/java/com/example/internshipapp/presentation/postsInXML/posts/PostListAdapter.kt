package com.example.internshipapp.presentation.postsInXML.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.internshipapp.R
import com.example.internshipapp.databinding.AdCardBinding
import com.example.internshipapp.databinding.PostItemBinding
import com.example.internshipapp.domain.entities.PostEntity

class PostListAdapter :
    ListAdapter<IPostsAndAdUiModels, PostScreenViewHolder>(PostAndAdDiffUtilItemCallback()) {

    var onPostClicked: ((PostEntity) -> Unit)? = null
    var onPostFavouriteButtonClicked: ((PostEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostScreenViewHolder {
        return when (viewType) {
            ADS_LIST_UI_MODEL_ID -> {
                val binding = AdCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PostScreenViewHolder(binding)
            }

            POSTS_LIST_UI_MODEL_ID -> {
                val binding = PostItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PostScreenViewHolder(binding)
            }

            else -> throw RuntimeException("Unknown ViewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item) {
            is IPostsAndAdUiModels.AdsUiModel -> ADS_LIST_UI_MODEL_ID
            is IPostsAndAdUiModels.PostsUiModel -> POSTS_LIST_UI_MODEL_ID
        }
    }

    override fun onBindViewHolder(holder: PostScreenViewHolder, position: Int) {

        when (val item = currentList[position]) {
            is IPostsAndAdUiModels.AdsUiModel -> {
                if (holder.binding is AdCardBinding) {
                    with(holder.binding) {
                        adTitle.text = item.ad.title
                        adMainText.text = item.ad.mainText
                    }
                }
            }

            is IPostsAndAdUiModels.PostsUiModel -> {
                if (holder.binding is PostItemBinding)
                    with(holder.binding) {
                        if (item.post.isFavourite) {
                            favouriteButton.setImageResource(R.drawable.baseline_favorite_24)
                        } else {
                            favouriteButton.setImageResource(R.drawable.baseline_favorite_border_24)
                        }
                        postTitle.text = item.post.title
                        postMainText.text = item.post.body

                        favouriteButton.setOnClickListener {
                            onPostFavouriteButtonClicked?.invoke(item.post)
                        }
                        root.setOnClickListener {
                            onPostClicked?.invoke(item.post)
                        }


                    }
            }
        }
    }

    companion object {
        private const val ADS_LIST_UI_MODEL_ID = 1
        private const val POSTS_LIST_UI_MODEL_ID = 2
    }
}