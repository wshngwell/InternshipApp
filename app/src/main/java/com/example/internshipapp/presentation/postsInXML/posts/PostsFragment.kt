package com.example.internshipapp.presentation.postsInXML.posts

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.internshipapp.R
import com.example.internshipapp.databinding.AlertDialogPostBinding
import com.example.internshipapp.databinding.FragmentPostsBinding
import com.example.internshipapp.presentation.feature2.PostsViewModel
import com.example.internshipapp.presentation.parseLoadingExceptionToStringResource
import com.example.internshipapp.presentation.postsInXML.NavigationInstance.Companion.myNavigate
import com.example.internshipapp.presentation.postsInXML.collectFlow
import com.example.internshipapp.presentation.postsInXML.comments.PostDetailsInfoFragment.Companion.getPostDetailFragmentInstance
import org.koin.androidx.viewmodel.ext.android.viewModel


class PostsFragment : Fragment(R.layout.fragment_posts) {

    private val binding: FragmentPostsBinding by viewBinding(FragmentPostsBinding::bind)

    private val postsViewModel: PostsViewModel by viewModel()
    private val postListAdapter: BaseAdapter = BaseAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.backArrow -> {
                        Log.e("КЛИК", "КЛИК")
                        true
                    }


                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        addListeners()
        observeEvents()
        binding.postsRecyclerView.adapter = postListAdapter
        collectFlow(postsViewModel.mapState { it.postEditingId }) {
            Log.e("PostAdapterItem", "$it")
            it?.let {
                showAlertDialog(it)
            }
        }

        val cursorMap = mutableMapOf<String, Int>()
        collectFlow(postsViewModel.mapState { it.postAndAdList }) {
            val adapterList = it.map { postAndListUiModel ->
                when (postAndListUiModel) {
                    is IPostsAndAdUiModels.AdsUiModel -> AdAdapterItem(
                        postAndListUiModel,
                        cursorMap[postAndListUiModel.ad.id] ?: 0
                    ) { text, cursor ->
                        cursorMap[postAndListUiModel.ad.id] = cursor
                        postsViewModel.sendIntent(
                            PostsViewModel.Intent.OnAddCardTextChanged(
                                postAndListUiModel.ad.id,
                                text
                            )
                        )
                    }

                    is IPostsAndAdUiModels.PostsUiModel -> {
                        PostAdapterItem(
                            postAndListUiModel,
                            onCLik = { post ->
                                postsViewModel.sendIntent(PostsViewModel.Intent.PostClicked(post))
                            },
                            onFavouriteClicked = { post ->
                                postsViewModel.sendIntent(
                                    PostsViewModel.Intent.FavouriteButtonClicked(
                                        post
                                    )
                                )
                            },
                            makeDialogVisible = {
                                postsViewModel.sendIntent(
                                    PostsViewModel.Intent.IdOfChangingPost(
                                        it
                                    )
                                )
                            }

                        )
                    }
                }

            }
            if (adapterList.isNotEmpty()) {
                postListAdapter.submitList(adapterList)
            }
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

    private fun showAlertDialog(changingPostId: Int) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_post, null)
        val alertDialogPostBinding = AlertDialogPostBinding.bind(dialogView)

        alertDialogPostBinding.changePostTitleEditText.setText(postsViewModel.state.value.postEditText)

        alertDialogPostBinding.changePostTitleEditText.doAfterTextChanged {
            postsViewModel.sendIntent(PostsViewModel.Intent.ChangePostEditText(it.toString()))
        }
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setOnDismissListener {
                postsViewModel.sendIntent(PostsViewModel.Intent.IdOfChangingPost(null))
            }
            .setOnCancelListener {
                postsViewModel.sendIntent(PostsViewModel.Intent.IdOfChangingPost(null))
            }
            .setCancelable(true)
            .create()


        alertDialogPostBinding.changeTitlePostButton.setOnClickListener {
            postsViewModel.sendIntent(
                PostsViewModel.Intent.OnPostCardTextChanged(
                    changingPostId,
                    alertDialogPostBinding.changePostTitleEditText.text.toString()
                )
            )
            alertDialog.dismiss()
        }

        alertDialog.show()
    }


    private fun addListeners() {

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
                //postsViewModel.sendIntent(PostsViewModel.Intent.FavouriteButtonClicked(currentPost))
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

}