package com.example.internshipapp.presentation.feature2

import CustomTextField
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.internshipapp.navigation.Screen
import com.example.internshipapp.navigation.myNavigate
import com.example.internshipapp.presentation.feature2.PostsViewModel.Intent
import com.example.internshipapp.presentation.parseLoadingExceptionToStringResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel


@Composable
fun PostsScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<PostsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val intent: (Intent) -> Unit by remember {
        mutableStateOf(viewModel::sendIntent)
    }
    val event: Flow<PostsViewModel.Event> by remember {
        mutableStateOf(viewModel.event)
    }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        event.filterIsInstance<PostsViewModel.Event>().collect {
            when (it) {
                is PostsViewModel.Event.OnPostClicked -> {
                    navController.myNavigate(
                        route = Screen.DetailedPostScreenWithComments.getRouteWithPost(
                            it.postEntity
                        )
                    )
                }

                is PostsViewModel.Event.Error -> {
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
        intent = intent,
    )

}

@Composable
@Preview
private fun UI(
    state: PostsViewModel.State = PostsViewModel.State(),
    intent: (Intent) -> Unit = {}
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

        CustomTextField(
            onFilterText = {
                intent(Intent.OnPostFilterTextChanged(state.filterText))
            },
            onValueChange = {
                intent(Intent.OnPostFilterTextChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            value = state.filterText
        )
        LazyColumn(
            modifier = Modifier.padding(top = 60.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.filteredListOfPostEntities, key = { it.id }) {
                SwipeablePost(
                    postEntity = it,
                    onPostClicked = {intent(Intent.PostClicked(it)) },
                    onFavouriteClicked = {intent(Intent.FavouriteButtonClicked(it)) }
                )
            }
        }
    }
}