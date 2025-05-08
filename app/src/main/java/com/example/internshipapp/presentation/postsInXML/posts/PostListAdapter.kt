package com.example.internshipapp.presentation.postsInXML.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.internshipapp.R
import com.example.internshipapp.databinding.PostItemBinding
import com.example.internshipapp.domain.entities.PostEntity

class PostListAdapter : ListAdapter<PostEntity, PostViewHolder>(PostDiffUtilItemCallback()) {

    var onPostClicked: ((PostEntity) -> Unit)? = null
    var onFavouriteButtonClicked: ((PostEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = PostItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = currentList[position]
        with(holder.binding) {
            if (post.isFavourite) {
                favouriteButton.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                favouriteButton.setImageResource(R.drawable.baseline_favorite_border_24)
            }
            postTitle.text = post.title
            postMainText.text = post.body

            favouriteButton.setOnClickListener {
                onFavouriteButtonClicked?.invoke(post)
            }
            root.setOnClickListener {
                onPostClicked?.invoke(post)
            }
        }
    }
}