package com.example.internshipapp.presentation.feature2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.entities.TResult
import com.example.internshipapp.domain.managers.IPostManager
import com.example.internshipapp.presentation.SingleFlowEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class PostsViewModel(
    private val manager: IPostManager
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow


    data class State(
        val filterText: String = "",
        val postsList: List<PostEntity> = listOf(),
        val filteredListOfPostEntities: List<PostEntity> = listOf(),
        val isLoading: Boolean = false,
    )

    sealed interface Event {
        data class OnPostClicked(val postEntity: PostEntity) : Event
        data class Error(val msg: String) : Event
    }

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val tPostResult = manager.getPostsFromNetwork()
            when (tPostResult) {
                is TResult.Error -> _event.emit(
                    Event.Error(msg = tPostResult.exception.message.toString())

                )

                is TResult.Success -> {
                    _state.update {
                        it.copy(
                            filterText = "",
                            postsList = tPostResult.data,
                            filteredListOfPostEntities = tPostResult.data,
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
    }

    fun sendIntent(intent: Intent) {
        when (intent) {
            is Intent.OnPostFilterTextChanged -> {
                val filteredListOfPosts = _state.value.postsList.toList().filter { post ->
                    post.title.contains(intent.text) || post.body.contains(intent.text)
                }
                _state.update {
                    it.copy(
                        filterText = intent.text,
                        filteredListOfPostEntities = filteredListOfPosts
                    )
                }
            }

            is Intent.PostClicked -> _event.emit(Event.OnPostClicked(intent.postEntity))
        }
    }
}