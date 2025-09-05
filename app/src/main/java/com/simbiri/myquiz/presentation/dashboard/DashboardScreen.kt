package com.simbiri.myquiz.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simbiri.myquiz.domain.QuizTopic
import com.simbiri.myquiz.presentation.common_component.ErrorScreen
import com.simbiri.myquiz.presentation.dashboard.component.ShimmerEffect
import com.simbiri.myquiz.presentation.dashboard.component.TopicCard
import com.simbiri.myquiz.presentation.dashboard.component.UserStatisticsCard

@Composable
fun DashBoardScreen(
    modifier: Modifier = Modifier,
    state: DashBoardState
) {
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderSection(
            modifier = Modifier
                .padding(top = 40.dp, start = 10.dp, end = 10.dp),
            userName = state.userName,
            questionsAttempted = state.questionsAttempted,
            correctAnswers = state.correctAnswers,
            onEditNameClick = {}
        )
        QuizTopicSection(
            modifier = Modifier
                .fillMaxWidth(),
            quizTopics = state.quizTopics,
            isTopicsLoading = state.isLoading,
            errorMessage = state.errorMessage,
            onRefreshIconClick = {}
        )
    }

}

@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier,
    userName: String, questionsAttempted: Int, correctAnswers: Int,
    onEditNameClick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = "Hello",
            style = MaterialTheme.typography.bodyMedium
        )

        Row {
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineMedium
            )

            IconButton(
                modifier = Modifier.offset(x = (-10).dp, y = (-20).dp),
                onClick = onEditNameClick
            ) {
                Icon(
                    modifier = Modifier.size(15.dp),
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Name"

                )
            }
        }



        UserStatisticsCard(
            questionsAttempted = questionsAttempted,
            correctAnswers = correctAnswers
        )
    }
}

@Composable
private fun QuizTopicSection(
    modifier: Modifier = Modifier,
    quizTopics: List<QuizTopic>,
    isTopicsLoading: Boolean,
    errorMessage: String?,
    onRefreshIconClick: () -> Unit
){
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(top= 30.dp, start = 10.dp, end = 10.dp),
            text = "What topic do you want to learn today?",
            style = MaterialTheme.typography.titleLarge
        )

        if (errorMessage != null){
            ErrorScreen(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                onRefreshIconClick = onRefreshIconClick,
                errorMessage = errorMessage
            )
        }else{

        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(15.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            if (isTopicsLoading){
                items(7){
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )
                }
            } else{
                items(quizTopics){ topic->
                    TopicCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        topicName = topic.name,
                        imageUrl = topic.imageUrl,
                        onClick = {}

                    )
                }
            }

        }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashBoardScreenPreview() {
    val dummyTopics = List(7){index->
        QuizTopic(
            id = "Dummy",
            name = "Ai and ML",
            imageUrl = "data:image/jpeg;base64,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",
            code = index
        )
    }
    DashBoardScreen(
        state = DashBoardState(
            questionsAttempted = 10,
            correctAnswers = 7,
            quizTopics = dummyTopics,
            isLoading = false,
            errorMessage = "Something went wrong"
        )
    )
}