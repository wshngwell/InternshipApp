package com.example.internshipapp.presentation.feature2

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipapp.domain.additionalEntitues.AdEntity
import com.example.internshipapp.domain.entities.LoadingException
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.entities.TResult
import com.example.internshipapp.domain.usecases.AddPostToFavouriteUseCase
import com.example.internshipapp.domain.usecases.DeletePostsFromFavouriteUseCase
import com.example.internshipapp.domain.usecases.GetFavouritePostsUseCase
import com.example.internshipapp.domain.usecases.GetPostsFromNetworkUseCase
import com.example.internshipapp.myLog
import com.example.internshipapp.presentation.SingleFlowEvent
import com.example.internshipapp.presentation.postsInXML.posts.IPostsAndAdUiModels
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
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

    fun <T> mapState(mMap: (State) -> T) = state.map(mMap).distinctUntilChanged()

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    private val postDisposable = CompositeDisposable()

    data class State(
        val filterText: String = "",
        val postEditText: String = "",
        val favouritePosts: List<PostEntity> = listOf(),
        val postAndAdList: List<IPostsAndAdUiModels> = listOf(),
        val loadedPostsListFromNetwork: List<PostEntity> = listOf(),
        val isLoading: Boolean = false,
        val postEditingId: Int? = null
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
                Log.e("COllect", favouritePostsList.size.toString())

                state.value.favouritePosts.firstOrNull { !favouritePostsList.contains(it) }
                    ?.let { changedPost ->
                        _state.update {
                            it.copy(loadedPostsListFromNetwork = state.value.loadedPostsListFromNetwork.map {
                                if (it == changedPost) it.copy(isFavourite = false) else it
                            })
                        }
                    }

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
            val disposable = getPostsFromNetworkUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { tPostResult ->
                    when (tPostResult) {
                        is TResult.Error -> _event.emit(
                            Event.Error(exception = tPostResult.exception)
                        )

                        is TResult.Success -> {
                            val postWithAdList = tPostResult.data.map {
                                if (it.id % 2 == 0) {
                                    IPostsAndAdUiModels.AdsUiModel(
                                        ad = AdEntity(
                                            id = it.id.toString(),
                                            title = "Title ${it.id}",
                                            mainText = "MainText ${it.id}"
                                        )
                                    )
                                } else {
                                    IPostsAndAdUiModels.PostsUiModel(
                                        post = it
                                    )
                                }
                            }
                            _state.update {
                                it.copy(
                                    postAndAdList = postWithAdList,
                                    loadedPostsListFromNetwork = tPostResult.data,
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            postDisposable.add(disposable)
            _state.update { it.copy(isLoading = false) }
        }

    }

    sealed interface Intent {
        data class OnPostFilterTextChanged(val text: String) : Intent
        data class PostClicked(val postEntity: PostEntity) : Intent
        data class FavouriteButtonClicked(val postEntity: PostEntity) : Intent
        data class IdOfChangingPost(val postId: Int?) : Intent
        data class ChangePostEditText(val text: String) : Intent
        data class OnAddCardTextChanged(
            val addId: String,
            val addCardText: String
        ) : Intent

        data class OnPostCardTextChanged(
            val postId: Int,
            val postCardText: String
        ) : Intent
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
                    } else {
                        addPostToFavouriteUseCase(intent.postEntity)
                    }
                    val newLoadedList = _state.value.loadedPostsListFromNetwork.map {
                        if (it == intent.postEntity) {
                            it.copy(isFavourite = !it.isFavourite)
                        } else {
                            it
                        }
                    }
                    _state.update { it.copy(loadedPostsListFromNetwork = newLoadedList) }
                }
            }

            is Intent.OnAddCardTextChanged -> {

                _state.update {
                    it.copy(
                        postAndAdList = state.value.postAndAdList
                            .toMutableList().apply {
                                replaceAll {
                                    if (it is IPostsAndAdUiModels.AdsUiModel && it.ad.id == intent.addId) {
                                        it.copy(ad = it.ad.copy(title = intent.addCardText))
                                    } else it
                                }

                            }
                    )


                }
            }


            is Intent.IdOfChangingPost -> {

                _state.update { it.copy(postEditingId = intent.postId) }
            }

            is Intent.OnPostCardTextChanged -> {
                _state.update {
                    it.copy(
                        postAndAdList = state.value.postAndAdList
                            .toMutableList().apply {
                                replaceAll {
                                    if (it is IPostsAndAdUiModels.PostsUiModel && it.post.id == intent.postId) {
                                        it.copy(post = it.post.copy(title = intent.postCardText))
                                    } else it
                                }

                            }
                    )
                }
            }

            is Intent.ChangePostEditText -> _state.update {
                it.copy(
                    postEditText = intent.text
                )
            }
        }
    }

    override fun onCleared() {
        postDisposable.dispose()
        super.onCleared()
    }
}