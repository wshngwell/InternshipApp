package com.example.internshipapp.presentation.postsInXML.posts

import android.view.View
import com.example.internshipapp.R
import com.example.internshipapp.databinding.AdCardBinding

data class AdAdapterItem(
    private val adUiModel: IPostsAndAdUiModels.AdsUiModel
) : BaseItem {

    override fun getItemViewType(): Int = R.layout.ad_card

    override fun getItemsSame(): String = adUiModel.ad.id

    override fun onBindViewHolder(view: View) {
        view.apply {
            val binding = AdCardBinding.bind(this)
            binding.adTitle.text = adUiModel.ad.title
            binding.adMainText.text = adUiModel.ad.mainText
        }
    }
}