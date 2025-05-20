package com.example.internshipapp.presentation.postsInXML

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.internshipapp.R
import com.example.internshipapp.databinding.ActivityPostsBinding

class PostsActivity : AppCompatActivity(R.layout.activity_posts) {

    private val binding: ActivityPostsBinding by viewBinding(ActivityPostsBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)

    }
}