package com.example.internshipapp.presentation.feature6

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.internshipapp.presentation.feature6.PaginationViewModel.Intent
import com.example.internshipapp.presentation.feature6.PaginationViewModel.State
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@RootNavGraph
@Destination
@Composable
fun PaginationScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = koinViewModel<PaginationViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val intent: (Intent) -> Unit by remember {
        mutableStateOf(viewModel::sendIntent)
    }
    UI(
        state = state,
        intent = intent
    )


}

@Composable
@Preview
private fun UI(
    state: State = State(),
    intent: (Intent) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(15.dp),
        contentAlignment = Alignment.TopCenter
    ) {

        LazyColumn(
            modifier = Modifier.padding(top = 60.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = state.dataList, key = { it }) {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 60.dp)
                        .background(Color.Red)
                )
                if (it == state.firstElementOfLastLoadedList) {
                    LaunchedEffect(Unit) {
                        intent(Intent.OnLoadNextPage)
                    }
                }
            }

            item {
                if (state.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.Blue)
                    }
                }
            }

        }
    }
}