package com.example.internshipapp.presentation.postsInXML.posts

import android.view.View
import com.example.internshipapp.R
import com.example.internshipapp.databinding.PostItemBinding
import com.example.internshipapp.domain.entities.PostEntity

data class PostAdapterItem(
    private val postUiModel: IPostsAndAdUiModels.PostsUiModel,
    private val onCLik: (PostEntity) -> Unit,
    private val onFavouriteClicked: (PostEntity) -> Unit
) : BaseItem {

    override fun getItemViewType(): Int = R.layout.post_item

    override fun getItemsSame(): String = postUiModel.post.id.toString()

    override fun onBindViewHolder(view: View) {
        view.apply {
            val binding = PostItemBinding.bind(this)
            binding.postTitle.text = postUiModel.post.title
            binding.postMainText.text = postUiModel.post.body

            val imageDrawableId =
                if (postUiModel.post.isFavourite) R.drawable.baseline_favorite_24
                else R.drawable.baseline_favorite_border_24

            binding.favouriteButton.setImageResource(imageDrawableId)

            binding.root.setOnClickListener {
                onCLik(postUiModel.post)
            }
            binding.favouriteButton.setOnClickListener {
                onFavouriteClicked(postUiModel.post)
            }
        }
    }
}