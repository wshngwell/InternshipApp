package com.example.internshipapp.presentation.postsInXML

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.getBooleanOrThrow
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.content.res.getStringOrThrow
import androidx.core.view.isVisible
import com.example.internshipapp.R
import com.example.internshipapp.databinding.CustomViewBinding

class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding by lazy {
        CustomViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }
    val titleView = binding.titleTextView
    val descTextView = binding.descriptionTextView
    val actionButtonView = binding.actionButton
    val inputEditTextView = binding.inputEditText


    private val xml = context.theme.obtainStyledAttributes(
        attrs,
        R.styleable.CustomView,
        R.attr.CustomViewStyle,
        R.style.CustomViewDefaultStyle
    )

    var title: String = xml.getStringOrThrow(R.styleable.CustomView_title)
        .apply { title = this }
        set(value) {
            field = value
            titleView.text = field
        }

    var description: String = xml.getStringOrThrow(R.styleable.CustomView_description)
        .apply { description = this }
        set(value) {
            field = value
            descTextView.text = field
        }

    var descriptionColor: Int = xml.getResourceIdOrThrow(R.styleable.CustomView_descriptionColor)
        .apply { descriptionColor = this }
        set(value) {
            field = value
            descTextView.setTextColor(ContextCompat.getColor(context, value))
        }


    var titleColor: Int = xml.getResourceIdOrThrow(R.styleable.CustomView_titleColor)
        .apply { titleColor = this }
        set(value) {
            field = value
            titleView.setTextColor(ContextCompat.getColor(context, value))
        }

    var buttonVisibility: Boolean = xml.getBooleanOrThrow(R.styleable.CustomView_buttonVisibility)
        .apply { buttonVisibility = this }
        set(value) {
            field = value
            actionButtonView.isVisible = value
        }

    var enteredText: String = xml.getStringOrThrow(R.styleable.CustomView_buttonVisibility)
        .apply { enteredText = this }
        set(value) {
            field = value
            if (value != inputEditTextView.text.toString()) {
                inputEditTextView.setText(value)
            }
        }


    init {
        xml.recycle()
    }


}