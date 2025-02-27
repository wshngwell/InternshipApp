package com.example.internshipapp.presentation.feature2

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.presentation.parseLoadingExceptionToStringResource
import com.example.internshipapp.ui.theme.headlinesTextSp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@RootNavGraph
@Destination
@Composable
fun DetailedPostWithCommentsScreen(
    navigator: DestinationsNavigator,
    postEntity: PostEntity
) {
    val viewModel =
        koinViewModel<PostWithCommentsViewModel>(parameters = { parametersOf(postEntity) })
    val state by viewModel.state.collectAsStateWithLifecycle()
    val intent: (PostWithCommentsViewModel.Intent) -> Unit by remember {
        mutableStateOf(viewModel::sendIntent)
    }
    val event: Flow<PostWithCommentsViewModel.Event> by remember {
        mutableStateOf(viewModel.event)
    }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        event.filterIsInstance<PostWithCommentsViewModel.Event>().collect {
            when (it) {
                is PostWithCommentsViewModel.Event.Error -> {
                    Toast.makeText(
                        context,
                        it.exception.parseLoadingExceptionToStringResource(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    UI(
        state = state,
        intent = intent
    )

}

@Composable
@Preview
fun UI(
    state: PostWithCommentsViewModel.State = PostWithCommentsViewModel.State(
        postEntity = PostEntity(
            body = "body",
            id = 0,
            title = "title",
            userId = -1,
            isFavourite = false
        )
    ),
    intent: (PostWithCommentsViewModel.Intent) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(15.dp)
    ) {
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                CustomProgressBar()
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Post",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Serif,
                    fontSize = headlinesTextSp
                )
            }
            item {
                Post(
                    postEntity = state.postEntity,
                    onFavouriteClicked = {
                        intent(PostWithCommentsViewModel.Intent.OnFavouriteClicked(state.postEntity))
                    }
                )
            }
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Comments",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Serif,
                    fontSize = headlinesTextSp
                )
            }
            items(items = state.commentsList, key = { it.id }) {
                CommentCard(commentEntity = it)
            }
        }
    }
}