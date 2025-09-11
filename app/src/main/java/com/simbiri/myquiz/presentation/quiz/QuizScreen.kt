package com.simbiri.myquiz.presentation.quiz

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.simbiri.myquiz.domain.model.QuizQuestion
import com.simbiri.myquiz.domain.model.UserAnswer
import com.simbiri.myquiz.presentation.common_component.ErrorScreen
import com.simbiri.myquiz.presentation.quiz.component.ExitQuizDialog
import com.simbiri.myquiz.presentation.quiz.component.QuizScreenLoadingContent
import com.simbiri.myquiz.presentation.quiz.component.QuizScreenTopBar
import com.simbiri.myquiz.presentation.quiz.component.QuizSubmitButtons
import com.simbiri.myquiz.presentation.quiz.component.SubmitQuizDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun QuizScreen(
    state: QuizState,
    event: Flow<QuizEvent>,
    navigateToDashBoardScreen: () -> Unit,
    navigateToResultScreen: () -> Unit,
    onAction: (QuizAction) -> Unit
) {

    val context = LocalContext.current
    // all code in the Launched Effect block will not be affected by recomposition of the screen
    // unless the key provided changes, which in our case Unit never will

    LaunchedEffect(key1 = Unit) {
        event.collect { event ->
            // we will only get the code from the event channel
            when (event) {
                is QuizEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                QuizEvent.NavigateToDashBoardScreen -> navigateToDashBoardScreen()
                QuizEvent.NavigateToResultScreen -> navigateToResultScreen()
            }
        }
    }
    SubmitQuizDialog(
        isDialogOpen = state.isSubmitQuizDialogOpen,
        onDialogDismiss = { onAction(QuizAction.SubmitQuizDialogDismiss)},
        onConfirmButtonClick = { onAction(QuizAction.ConfirmSubmitQuizButtonClick)}
    )

    ExitQuizDialog(
        isDialogOpen = state.isExitQuizDialogOpen,
        onDialogDismiss = { onAction(QuizAction.ExitQuizDialogDismiss)},
        onConfirmButtonClick = { onAction(QuizAction.ConfirmExitQuizButtonClick)}
    )

    Column(modifier = Modifier.fillMaxSize()) {
        QuizScreenTopBar(
            title = state.topBarTitle,
            onExitQuizClick = { onAction(QuizAction.ExitQuizButtonClick) },
        )

        if (state.isLoading) {
            QuizScreenLoadingContent(
                modifier = Modifier.fillMaxSize(),
                loadingMessage = state.loadingMessage ?: "Loading Quiz..."
            )
        } else {
            when {
                state.errorMessage != null -> {
                    ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        onRefreshIconClick = { onAction(QuizAction.RefreshQuizButtonClick)},
                        errorMessage = state.errorMessage
                    )
                }

                state.questionsList.isEmpty() -> {
                    ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        onRefreshIconClick = { onAction(QuizAction.RefreshQuizButtonClick)},
                        errorMessage = "No questions available"
                    )
                }

                else -> {
                    QuizScreenContent(
                        state = state,
                        onAction = onAction
                    )
                }
            }
        }


    }

}

@Composable
private fun QuizScreenContent(
    modifier: Modifier = Modifier,
    state: QuizState,
    onAction: (QuizAction) -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = { state.questionsList.size }
    )

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { pageIdx ->
            onAction(QuizAction.JumpToQuestion(pageIdx))
        }
    }

    LaunchedEffect(key1 = state.currentQuestionIdx) {
        pagerState.animateScrollToPage(state.currentQuestionIdx)
    }


    Column(
        modifier = modifier.fillMaxSize()
    ) {
        QuestionNavigationRow(
            questions = state.questionsList,
            currentQuestionIdx = state.currentQuestionIdx,
            chosenAnswers = state.chosenAnswers,
            onTabSelected = { idx ->
                onAction(QuizAction.JumpToQuestion(idx))
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalPager(

            modifier = Modifier
                .weight(1f),
            state = pagerState
        ) {
            QuestionItem(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .verticalScroll(rememberScrollState()),
                currentQuestionIdx = state.currentQuestionIdx,
                questions = state.questionsList,
                answers = state.chosenAnswers,
                onOptionSelected = { questionId, answer ->
                    onAction(QuizAction.OnOptionSelected(questionId, answer))
                }
            )
        }

        QuizSubmitButtons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            isPreviousEnabled = state.currentQuestionIdx != 0,
            isNextEnabled = state.currentQuestionIdx != state.questionsList.lastIndex,
            onPreviousClick = { onAction(QuizAction.PrevQuestionButtonClick) },
            onNextClick = { onAction(QuizAction.NextQuestionButtonClick) },
            onSubmitClick = { onAction(QuizAction.SubmitQuizButtonClick) }

        )
    }


}

@Composable
private fun QuestionItem(
    modifier: Modifier = Modifier,
    currentQuestionIdx: Int,
    questions: List<QuizQuestion>,
    answers: List<UserAnswer>,
    onOptionSelected: (String, String) -> Unit
) {
    val currentQuestion = questions[currentQuestionIdx]
    val selectedAnswer = answers.find { it.questionId == currentQuestion.id }?.selectedOption

    Column(modifier = modifier) {
        Text(
            text = currentQuestion.question,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            currentQuestion.allOptions.forEach { option ->
                OptionItem(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .widthIn(min = 400.dp),
                    option = option,
                    isSelected = option == selectedAnswer,
                    onClick = { onOptionSelected(currentQuestion.id, option) }
                )
            }
        }
    }
}

@Composable
private fun OptionItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    option: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick
            )
            Text(
                text = option,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun QuestionNavigationRow(
    modifier: Modifier = Modifier,
    currentQuestionIdx: Int,
    questions: List<QuizQuestion>,
    chosenAnswers: List<UserAnswer>,
    onTabSelected: (Int) -> Unit
) {
    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = currentQuestionIdx,
        edgePadding = 0.dp
    ) {
        questions.forEachIndexed { idx, question ->
            val tabColor = when {
                chosenAnswers.any { it.questionId == question.id } -> {
                    MaterialTheme.colorScheme.secondaryContainer
                }

                else -> {
                    MaterialTheme.colorScheme.surface
                }
            }
            Tab(
                modifier = Modifier.background(tabColor),
                selected = idx == currentQuestionIdx,
                onClick = { onTabSelected(idx) }
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 10.dp),
                    text = "${idx + 1}"
                )
            }

        }

    }
}

//@Preview(showBackground = true)
@PreviewScreenSizes
@Composable
fun QuizScreenPreview() {
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
        UserAnswer("1", "New Delhi"),
        UserAnswer("3", "Mumbai"),
    )
    QuizScreen(
        state = QuizState(
            questionsList = dummyQns,
            chosenAnswers = dummyAnswers,
        ),
        navigateToDashBoardScreen = {},
        navigateToResultScreen = {},
        onAction = {},
        event = emptyFlow()
    )
}