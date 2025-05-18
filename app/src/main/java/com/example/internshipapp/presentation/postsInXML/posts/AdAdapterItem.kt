package com.example.internshipapp.presentation.postsInXML.posts

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.internshipapp.R
import com.example.internshipapp.databinding.AdCardBinding

data class AdAdapterItem(
    private val adUiModel: IPostsAndAdUiModels.AdsUiModel,
    private val cursor: Int,
    private val onTextChanged: (String, Int) -> Unit
) : BaseItem {

    override fun getItemViewType(): Int = R.layout.ad_card

    override fun getItemsSame(): String = adUiModel.ad.id

    override fun onBindViewHolder(view: View) {
        view.apply {
            val binding = AdCardBinding.bind(this)
            binding.adTitle.text = adUiModel.ad.title
            binding.adMainText.text = adUiModel.ad.mainText

            binding.addEditText.apply {
                setText(adUiModel.ad.title)
                setSelection(cursor)
                val textWatcher = object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                    override fun afterTextChanged(p0: Editable?) {
                        onTextChanged(p0.toString(), binding.addEditText.selectionStart)
                    }
                }
                this.setOnFocusChangeListener { view, b ->
                    if (b) {
                        addTextChangedListener(textWatcher)
                    } else {
                        removeTextChangedListener(textWatcher)
                    }
                }


            }
        }
    }
}