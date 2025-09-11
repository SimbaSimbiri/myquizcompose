package com.simbiri.myquiz.presentation.result

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simbiri.myquiz.R
import com.simbiri.myquiz.domain.model.QuizQuestion
import com.simbiri.myquiz.domain.model.UserAnswer
import com.simbiri.myquiz.presentation.theme.customGreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    state: ResultState,
    event: Flow<ResultEvent>,
    onReportClick: (String) -> Unit,
    onStartNewQuizClick: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1= Unit) {
        event.collect { event ->
            when (event) {
                is ResultEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(10.dp)
        ) {
            item {
                ScoreCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 80.dp, horizontal = 10.dp),
                    scorePercentage = state.scorePercentage,
                    correctAnswers = state.correctAnswers,
                    totalQuestions = state.totalQuestions
                )
            }
            item {
                Text(
                    text = "Quiz Questions",
                    style = MaterialTheme.typography.titleLarge,
                    textDecoration = TextDecoration.Underline
                )
            }
            items(state.quizQuestions) { question ->
                val userAnswer = state.userAnswers.find { it.questionId == question.id }
                QuestionItem(
                    question = question,
                    userSelectedAnswer = userAnswer?.selectedOption,
                    onReportIconClick = { onReportClick(question.id) }
                )

            }
        }

        Button(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterHorizontally),
            onClick = onStartNewQuizClick
        ) {
            Text("Start New Quiz")
        }


    }


}

@Composable
fun ScoreCard(
    modifier: Modifier = Modifier,
    scorePercentage: Int,
    correctAnswers: Int,
    totalQuestions: Int
) {
    val resultText = when (scorePercentage) {
        in 71..100 -> "Congratulations! You did great!"
        in 51..70 -> "Good job! But there's room for improvement."
        else -> "Oops! Study harder next time"
    }
    val resultIconId = when (scorePercentage) {
        in 71..100 -> R.drawable.ic_happy
        in 51..70 -> R.drawable.ic_neutral
        else -> R.drawable.ic_sad
    }
    Card(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
                .size(100.dp),
            painter = painterResource(id = resultIconId), contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = " You got $correctAnswers out of $totalQuestions right",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp),
            text = resultText,
            style = MaterialTheme.typography.bodyMedium,

            )
    }

}

@Composable
private fun QuestionItem(
    modifier: Modifier = Modifier,
    question: QuizQuestion,
    userSelectedAnswer: String?,
    onReportIconClick: () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Q: ${question.question}",
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = onReportIconClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Report"
                )
            }

        }
        question.allOptions.forEachIndexed { idx, option ->
            val letterChoice = when (idx) {
                0 -> "(a)"
                1 -> "(b)"
                2 -> "(c)"
                3 -> "(d)"
                else -> ""
            }
            val optionColor = when (option) {
                question.correctAnswer -> customGreen
                userSelectedAnswer -> MaterialTheme.colorScheme.error
                else -> LocalContentColor.current
            }
            Text(
                text = "$letterChoice $option",
                color = optionColor
            )
        }
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = question.explanation,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        HorizontalDivider()
        Spacer(modifier= Modifier.height(20.dp))

    }
}

@Preview(showBackground = true)
@Composable
private fun ResultScreenPreview() {

    val dummyQns = List(5) { idx ->
        QuizQuestion(
            id = "$idx",
            question = "What is the capital of India?",
            correctAnswer = "New Delhi",
            allOptions = listOf("New Delhi", "Mumbai", "Kolkata", "Chennai"),
            explanation = "The capital of India is New Delhi.",
            topicCode = 1
        )
    }
    val dummyAnswers = listOf(
        UserAnswer("1", "Mumbai"),
        UserAnswer("0", "New Delhi"),
    )

    ResultScreen(
        state = ResultState(
            scorePercentage = 80,
            correctAnswers = 7,
            totalQuestions = 10,
            quizQuestions = dummyQns,
            userAnswers = dummyAnswers
        ),
        onReportClick = {},
        onStartNewQuizClick = {},
        event = emptyFlow()
    )
}