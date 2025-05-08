package com.example.internshipapp.presentation.feature2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipapp.domain.entities.CommentEntity
import com.example.internshipapp.domain.entities.LoadingException
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.entities.TResult
import com.example.internshipapp.domain.usecases.AddPostToFavouriteUseCase
import com.example.internshipapp.domain.usecases.DeletePostsFromFavouriteUseCase
import com.example.internshipapp.domain.usecases.GetCommentsUseCase
import com.example.internshipapp.domain.usecases.GetFavouritePostsUseCase
import com.example.internshipapp.presentation.SingleFlowEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostWithCommentsViewModel(
    private val getCommentsUseCase: GetCommentsUseCase,
    private val getFavouritePostsUseCase: GetFavouritePostsUseCase,
    private val deletePostUseCase: DeletePostsFromFavouriteUseCase,
    private val post: PostEntity,
    private val addPostToFavouriteUseCase: AddPostToFavouriteUseCase,
) : ViewModel() {

    data class State(
        val commentsList: List<CommentEntity> = listOf(),
        val postEntity: PostEntity,
        val favouritePosts: List<PostEntity> = listOf(),
        val isFavourite: Boolean = false,
        val isLoading: Boolean = false,
    )

    private val _state = MutableStateFlow(State(postEntity = post))
    val state = _state.asStateFlow()

    fun <T> mapState(mMap: (State) -> T) =
        state.map(mMap).distinctUntilChanged()

    sealed interface Event {
        data class Error(val exception: LoadingException) : Event
    }

    sealed interface Intent {
        data class OnFavouriteClicked(val postEntity: PostEntity) : Intent
    }

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    init {
        viewModelScope.launch {
            getFavouritePostsUseCase().collect { favouritePosts ->
                val favouriteIds = favouritePosts.map { it.id }

                _state.update { it.copy(isFavourite = favouriteIds.contains(post.id)) }
                _state.update { it.copy(favouritePosts = favouritePosts) }
            }
        }
    }

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val tCommentsResult = getCommentsUseCase(postId = post.id)
            when (tCommentsResult) {
                is TResult.Error -> _event.emit(
                    Event.Error(
                        exception = tCommentsResult.exception
                    )
                )

                is TResult.Success -> {
                    val listOfComments = tCommentsResult.data
                    _state.update { it.copy(commentsList = listOfComments) }
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun sendIntent(intent: Intent) {
        when (intent) {
            is Intent.OnFavouriteClicked -> {
                viewModelScope.launch {
                    val favouriteIds = state.value.favouritePosts.map { it.id }
                    if (favouriteIds.contains(intent.postEntity.id)) {
                        deletePostUseCase(intent.postEntity.id)
                        _state.update { it.copy(postEntity = it.postEntity.copy(isFavourite = false)) }
                    } else {
                        addPostToFavouriteUseCase(intent.postEntity)
                        _state.update { it.copy(postEntity = it.postEntity.copy(isFavourite = true)) }
                    }
                }
            }
        }
    }


}