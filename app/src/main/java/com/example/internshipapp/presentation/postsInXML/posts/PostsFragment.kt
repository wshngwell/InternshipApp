package com.example.internshipapp.presentation.postsInXML.posts

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.internshipapp.R
import com.example.internshipapp.databinding.FragmentPostsBinding
import com.example.internshipapp.presentation.GsonUtil.toJson
import com.example.internshipapp.presentation.feature2.PostsViewModel
import com.example.internshipapp.presentation.parseLoadingExceptionToStringResource
import com.example.internshipapp.presentation.postsInXML.NavigationInstance.Companion.myNavigate
import com.example.internshipapp.presentation.postsInXML.collectFlow
import com.example.internshipapp.presentation.postsInXML.comments.PostDetailsInfoFragment.Companion.getPostDetailFragmentInstance
import org.koin.androidx.viewmodel.ext.android.viewModel


class PostsFragment : Fragment(R.layout.fragment_posts) {

    private val binding: FragmentPostsBinding by viewBinding(FragmentPostsBinding::bind)
    private val postsViewModel: PostsViewModel by viewModel()
    private var postListAdapter: PostListAdapter = PostListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
        observeEvents()
        binding.postsRecyclerView.adapter = postListAdapter

        collectFlow(postsViewModel.mapState { it.filteredListOfPostEntities }) {
            postListAdapter.submitList(it)
        }
        collectFlow(postsViewModel.mapState { it.isLoading }) {
            if (it) {
                binding.linearLayoutWithProgressBarPostsScreen.visibility = View.VISIBLE
                binding.linearLayoutWithDefaultContent.visibility = View.GONE
            } else {
                binding.linearLayoutWithProgressBarPostsScreen.visibility = View.GONE
                binding.linearLayoutWithDefaultContent.visibility = View.VISIBLE
            }
        }
    }

    private fun addListeners() {
        postListAdapter.onPostClicked = {
            postsViewModel.sendIntent(PostsViewModel.Intent.PostClicked(it))
        }
        postListAdapter.onFavouriteButtonClicked = {
            postsViewModel.sendIntent(PostsViewModel.Intent.FavouriteButtonClicked(it))
        }
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val currentPost = postListAdapter.currentList[viewHolder.absoluteAdapterPosition]
                postsViewModel.sendIntent(PostsViewModel.Intent.FavouriteButtonClicked(currentPost))
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.postsRecyclerView)
        binding.searchPostsEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                text: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                postsViewModel.sendIntent(PostsViewModel.Intent.OnPostFilterTextChanged(text.toString()))
            }

            override fun afterTextChanged(text: Editable?) {}
        })
    }

    private fun observeEvents() {
        collectFlow(postsViewModel.event) {
            when (it) {
                is PostsViewModel.Event.Error -> {
                    Toast.makeText(
                        context,
                        it.exception.parseLoadingExceptionToStringResource(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is PostsViewModel.Event.OnPostClicked -> {
                    findNavController().myNavigate(
                        instance = getPostDetailFragmentInstance(it.postEntity)
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.postsRecyclerView.adapter = null
        super.onDestroyView()
    }
}