package com.example.internshipapp.presentation.feature2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.ui.theme.defaultTextSp
import com.example.internshipapp.ui.theme.headlinesTextSp
import com.example.internshipapp.ui.theme.onPostColorContent
import com.example.internshipapp.ui.theme.postColor

@Composable
fun Post(
    postEntity: PostEntity,
    onPostClicked: () -> Unit = {},
    onFavouriteClicked: () -> Unit = {}
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(postColor)
            .clickable { onPostClicked() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(postColor)
                .padding(10.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(postColor)
            ) {
                FavoriteButton(
                    isFavorite = postEntity.isFavourite,
                    onClick = {
                        onFavouriteClicked()
                    })
            }
            Text(
                text = postEntity.title,
                color = onPostColorContent,
                fontWeight = FontWeight.Bold,
                fontSize = headlinesTextSp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = postEntity.body,
                color = onPostColorContent,
                fontWeight = FontWeight.SemiBold,
                fontSize = defaultTextSp,
            )
        }
    }
}