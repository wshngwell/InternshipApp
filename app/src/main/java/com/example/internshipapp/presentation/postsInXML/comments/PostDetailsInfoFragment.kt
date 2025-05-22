package com.example.internshipapp.presentation.postsInXML.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.example.internshipapp.R
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.presentation.GsonUtil.toJson
import com.example.internshipapp.presentation.feature2.DetailedPostWithCommentsScreen
import com.example.internshipapp.presentation.postsInXML.NavigationInstance


class PostDetailsInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                DetailedPostWithCommentsScreen(
                    postEntityGson = arguments?.getString(POST_KEY)
                        ?: throw RuntimeException("no post")
                )
            }
        }
    }

    companion object {

        private const val POST_KEY = "POST_KEY"

        fun getPostDetailFragmentInstance(postEntity: PostEntity) = NavigationInstance(
            id = R.id.action_postsFragment_to_postDetailsInfoFragment,
            bundle = Bundle().apply {
                val postEntityJson = postEntity.toJson()
                putString(POST_KEY, postEntityJson)
            }
        )


    }
}