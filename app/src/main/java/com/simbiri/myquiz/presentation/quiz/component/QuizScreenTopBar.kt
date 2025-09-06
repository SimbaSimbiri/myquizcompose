package com.simbiri.myquiz.presentation.quiz.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreenTopBar(
    modifier: Modifier = Modifier,
    title: String = "AndroidQuiz",
    onExitQuizClick: () -> Unit,
) {
    TopAppBar(
        modifier=modifier,
        title = { Text(title) },
        actions = {
            IconButton(onClick = onExitQuizClick) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Exit"
                )
            }
        }
    )
}

@Preview
@Composable
fun QuizScreenTopBarPreview() {
    QuizScreenTopBar(
        title = "Android Quiz",
        onExitQuizClick = {}
    )
}