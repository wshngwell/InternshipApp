package com.example.internshipapp.presentation.feature2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipapp.domain.entities.LoadingException
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.entities.TResult
import com.example.internshipapp.domain.usecases.GetPostsFromNetworkUseCase
import com.example.internshipapp.domain.usecases.AddPostToFavouriteUseCase
import com.example.internshipapp.domain.usecases.DeletePostsFromFavouriteUseCase
import com.example.internshipapp.domain.usecases.GetFavouritePostsUseCase
import com.example.internshipapp.myLog
import com.example.internshipapp.presentation.SingleFlowEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostsViewModel(
    private val getPostsFromNetworkUseCase: GetPostsFromNetworkUseCase,
    private val getFavouritePostsUseCase: GetFavouritePostsUseCase,
    private val addPostToFavouriteUseCase: AddPostToFavouriteUseCase,
    private val deletePostsFromFavouriteUseCase: DeletePostsFromFavouriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow


    data class State(
        val filterText: String = "",
        val favouritePosts: List<PostEntity> = listOf(),
        val loadedPostsListFromNetwork: List<PostEntity> = listOf(),
        val isLoading: Boolean = false,
    ) {
        val filteredListOfPostEntities: List<PostEntity> =
            (favouritePosts + loadedPostsListFromNetwork)
                .distinctBy { it.id }
                .sortedBy { !it.isFavourite }
                .filter { post ->
                    myLog("${post.id}")
                    post.title.contains(filterText) || post.body.contains(filterText)
                }
    }

    sealed interface Event {
        data class OnPostClicked(val postEntity: PostEntity) : Event
        data class Error(val exception: LoadingException) : Event
    }

    init {
        viewModelScope.launch {
            getFavouritePostsUseCase().collect { favouritePostsList ->
                myLog("COLLECT $favouritePostsList")
                _state.update {
                    it.copy(
                        favouritePosts = favouritePostsList,
                    )
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val tPostResult = getPostsFromNetworkUseCase()
            when (tPostResult) {
                is TResult.Error -> _event.emit(
                    Event.Error(exception = tPostResult.exception)
                )

                is TResult.Success -> {
                    _state.update {
                        it.copy(
                            loadedPostsListFromNetwork = tPostResult.data,
                            isLoading = false
                        )
                    }
                }
            }
            _state.update { it.copy(isLoading = false) }
        }

    }


    sealed interface Intent {
        data class OnPostFilterTextChanged(val text: String) : Intent
        data class PostClicked(val postEntity: PostEntity) : Intent
        data class FavouriteButtonClicked(val postEntity: PostEntity) : Intent
    }

    fun sendIntent(intent: Intent) {
        when (intent) {
            is Intent.OnPostFilterTextChanged -> {
                _state.update { it.copy(filterText = intent.text) }
            }

            is Intent.PostClicked -> _event.emit(Event.OnPostClicked(intent.postEntity))
            is Intent.FavouriteButtonClicked -> {
                viewModelScope.launch {
                    val favouriteIds = state.value.favouritePosts.map { it.id }
                    if (favouriteIds.contains(intent.postEntity.id)) {
                        deletePostsFromFavouriteUseCase(intent.postEntity.id)
                        val newLoadedList = _state.value.loadedPostsListFromNetwork.map {
                            if (it == intent.postEntity) {
                                it.copy(isFavourite = false)
                            } else {
                                it
                            }
                        }
                        _state.update { it.copy(loadedPostsListFromNetwork = newLoadedList) }
                    } else {
                        addPostToFavouriteUseCase.addPostToFavourite(intent.postEntity)
                    }
                }
            }
        }
    }
}