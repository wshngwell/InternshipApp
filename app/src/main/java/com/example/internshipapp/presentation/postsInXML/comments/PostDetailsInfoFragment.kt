package com.example.internshipapp.presentation.postsInXML.comments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.internshipapp.R
import com.example.internshipapp.databinding.FragmentPostDetailsInfoBinding
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.presentation.GsonUtil.fromJson
import com.example.internshipapp.presentation.GsonUtil.toJson
import com.example.internshipapp.presentation.feature2.PostWithCommentsViewModel
import com.example.internshipapp.presentation.parseLoadingExceptionToStringResource
import com.example.internshipapp.presentation.postsInXML.NavigationInstance
import com.example.internshipapp.presentation.postsInXML.collectFlow
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PostDetailsInfoFragment : Fragment(R.layout.fragment_post_details_info) {

    private val viewModel: PostWithCommentsViewModel by viewModel {
        parametersOf(arguments?.getString(POST_KEY)?.fromJson<PostEntity>())
    }
    private val binding: FragmentPostDetailsInfoBinding by viewBinding(
        FragmentPostDetailsInfoBinding::bind
    )

    private val commentsListAdapter: CommentListAdapter = CommentListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.commentsRecyclerView.adapter = commentsListAdapter
        observeEvents()
        collectFlow(viewModel.mapState { it.isLoading }) {
            if (it) {
                binding.linearLayoutWithCommentsProgressBar.isVisible = true
                binding.linearLayoutCommentsScreen.isVisible = false
            } else {
                binding.linearLayoutCommentsScreen.isVisible = true
                binding.linearLayoutWithCommentsProgressBar.isVisible = false
            }
        }
        collectFlow(viewModel.mapState { it.postEntity }) {
            with(binding) {
                customView.title = viewModel.state.value.postEntity.title
                customView.description = viewModel.state.value.postEntity.body
                customView.buttonVisibility = true
                customView.actionButtonView.setOnClickListener {
                    Log.e("PostDetailsInfoFragment", "CLICKED")
                }

                customView.inputEditTextView.doAfterTextChanged {
                    viewModel.sendIntent(PostWithCommentsViewModel.Intent.TypedText(it.toString()))
                }
            }
        }

        collectFlow(viewModel.mapState { it.typedText }) {
            binding.customView.enteredText = it
        }


        collectFlow(viewModel.mapState { it.commentsList }) {
            commentsListAdapter.submitList(it)
        }
    }

    private fun observeEvents() {
        collectFlow(viewModel.event) {
            when (it) {
                is PostWithCommentsViewModel.Event.Error -> Toast.makeText(
                    context,
                    it.exception.parseLoadingExceptionToStringResource(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        binding.commentsRecyclerView.adapter = null
        super.onDestroyView()
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