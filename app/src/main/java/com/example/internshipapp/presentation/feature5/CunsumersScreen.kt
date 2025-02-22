package com.example.internshipapp.presentation.feature5

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.internshipapp.data.local.dbModels.ConsumerDbModel
import com.example.internshipapp.presentation.feature5.ConsumersViewModel.*
import org.koin.androidx.compose.koinViewModel


@Composable
fun ConsumersScreen(
    navController: NavController
) {

    val viewModel = koinViewModel<ConsumersViewModel>()
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
        Button(
            modifier = Modifier.padding(top = 20.dp),
            onClick = {
                intent(Intent.OnAddToFavourite)
            }
        ) {
            Text(text = "Добавить в базу данных")
        }

        LazyColumn(
            modifier = Modifier.padding(top = 75.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = state.consumersList, key = { it.id }) {
                ConsumerCard(
                    consumerDbModel = it,
                    intent = intent
                )
            }
        }
    }
}

@Composable
@Preview
private fun ConsumerCard(
    consumerDbModel: ConsumerDbModel = ConsumerDbModel(
        id = 0,
        A = "AAAAA",
        B = "BBBBB",
        C = "CCCCC",
        D = "DDDDD"
    ),
    intent: (Intent) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardColors(
            contentColor = Color.White,
            containerColor = Color.Blue,
            disabledContentColor = Color.White,
            disabledContainerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = consumerDbModel.A + " " + consumerDbModel.id,
                )
                Text(
                    text = consumerDbModel.B+ " " + consumerDbModel.id,
                )
                Text(
                    text = consumerDbModel.C+ " " + consumerDbModel.id,
                )
                Text(
                    text = consumerDbModel.D+ " " + consumerDbModel.id,
                )
            }

            Button(
                modifier = Modifier.padding(top = 20.dp, end = 20.dp),
                onClick = {
                    intent(Intent.OnRemoveFromFavourite(consumerDbModel))
                }
            ) {
                Text(text = "Удалить из бд")
            }

        }

    }

}