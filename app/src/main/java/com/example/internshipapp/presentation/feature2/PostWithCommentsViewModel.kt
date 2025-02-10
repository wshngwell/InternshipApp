package com.example.internshipapp.presentation.feature2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipapp.domain.entities.CommentEntity
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.entities.TResult
import com.example.internshipapp.domain.managers.IPostManager
import com.example.internshipapp.presentation.SingleFlowEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostWithCommentsViewModel(
    private val manager: IPostManager,
    private val postEntity: PostEntity,
) : ViewModel() {

    data class State(
        val commentsList: List<CommentEntity> = listOf(),
        val postEntity: PostEntity,
        val isLoading: Boolean = false,
    )

    private val _state = MutableStateFlow(State(postEntity = postEntity))
    val state = _state.asStateFlow()

    sealed interface Event {
        data class Error(val msg: String) : Event
    }

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    init {
        viewModelScope.launch() {
            _state.update { it.copy(isLoading = true) }
            val tCommentsResult = manager.getCommentsToPostFromNetwork(postId = postEntity.id)
            when (tCommentsResult) {
                is TResult.Error -> _event.emit(
                    Event.Error(
                        msg = tCommentsResult.exception.message.toString()
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


}