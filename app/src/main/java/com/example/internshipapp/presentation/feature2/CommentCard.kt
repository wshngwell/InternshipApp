package com.example.internshipapp.presentation.feature2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.internshipapp.domain.entities.CommentEntity
import com.example.internshipapp.ui.theme.commentColor
import com.example.internshipapp.ui.theme.commentsBodySp
import com.example.internshipapp.ui.theme.commentsMailSp
import com.example.internshipapp.ui.theme.commentsNameSp
import com.example.internshipapp.ui.theme.postColor


@Composable
fun CommentCard(
    commentEntity: CommentEntity
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(commentColor)
                .padding(10.dp),
        ) {
            Text(
                text = commentEntity.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = commentsNameSp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = commentEntity.email,
                color = postColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = commentsMailSp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = commentEntity.body,
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = commentsBodySp,
            )
        }

    }
}