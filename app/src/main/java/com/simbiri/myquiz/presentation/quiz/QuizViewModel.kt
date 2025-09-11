package com.simbiri.myquiz.presentation.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.simbiri.myquiz.domain.model.UserAnswer
import com.simbiri.myquiz.domain.repository.QuizQuestionRepository
import com.simbiri.myquiz.domain.repository.QuizTopicRepository
import com.simbiri.myquiz.domain.util.onFailure
import com.simbiri.myquiz.domain.util.onSuccess
import com.simbiri.myquiz.presentation.navigation.Route
import com.simbiri.myquiz.presentation.util.getErrorMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel(
    private val quizQuestionRepo: QuizQuestionRepository,
    private val topicRepository: QuizTopicRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    private val _event = Channel<QuizEvent>()
    val event =  _event.receiveAsFlow()

    private val topicCode = savedStateHandle.toRoute<Route.QuizScreen>().topicCode

    init {
        setUpQuiz()
    }

    fun onAction(action: QuizAction) {
        when (action) {
            QuizAction.NextQuestionButtonClick -> {
                val nextIndex = (state.value.currentQuestionIdx + 1)
                    .coerceAtMost(state.value.questionsList.lastIndex)
                _state.update { it.copy(currentQuestionIdx = nextIndex) }
            }

            QuizAction.PrevQuestionButtonClick -> {
                val prevIndex = (state.value.currentQuestionIdx - 1)
                    .coerceAtLeast(0)
                _state.update { it.copy(currentQuestionIdx = prevIndex) }

            }

            is QuizAction.JumpToQuestion -> {
                _state.update { it.copy(currentQuestionIdx = action.questionIdx) }
            }

            is QuizAction.OnOptionSelected -> {

                val newAnswer = UserAnswer(action.questionId, action.answer)
                val currAnswers = state.value.chosenAnswers.toMutableList()

                val idx = currAnswers.indexOfFirst { it.questionId == action.questionId }

                if (idx != -1) currAnswers[idx] = newAnswer
                else currAnswers.add(newAnswer)

                currAnswers.add(newAnswer)

                _state.update { it.copy(chosenAnswers = currAnswers) }
            }

            QuizAction.ExitQuizButtonClick -> {
                _state.update { it.copy(isExitQuizDialogOpen = true) }
            }
            QuizAction.ConfirmExitQuizButtonClick -> {
                _state.update { it.copy(isExitQuizDialogOpen = false) }
                _event.trySend(QuizEvent.NavigateToDashBoardScreen)
            }
            QuizAction.ExitQuizDialogDismiss -> {
            _state.update { it.copy(isExitQuizDialogOpen = false) }
            }
            QuizAction.SubmitQuizButtonClick -> {
                _state.update { it.copy(isSubmitQuizDialogOpen = true) }
            }
            QuizAction.ConfirmSubmitQuizButtonClick -> {
                _state.update { it.copy(isSubmitQuizDialogOpen = false) }
                _event.trySend(QuizEvent.NavigateToResultScreen)
            }
            QuizAction.SubmitQuizDialogDismiss -> {
                _state.update { it.copy(isSubmitQuizDialogOpen = false) }
            }
            QuizAction.RefreshQuizButtonClick -> {
                setUpQuiz()
            }

        }

    }


    private suspend fun getQuizTopicByCode(topicCode: Int) {
        topicRepository.getQuizTopicsByCode(topicCode)
            .onSuccess { topic ->
                val topicName = topic.name.split(" ").first()
                _state.update { it.copy(topBarTitle = "$topicName Quiz") }

            }
            .onFailure { error ->
                _event.send(QuizEvent.ShowToast(error.getErrorMessage()))

            }

    }

    private fun setUpQuiz() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    loadingMessage = "Setting up Quiz"
                )
            }
            getQuizTopicByCode(topicCode)
            getQuizQuestions(topicCode)
            _state.update {
                it.copy(
                    isLoading = false,
                    loadingMessage = null
                )
            }
        }
    }

    private suspend fun getQuizQuestions(topicCode: Int) {
        quizQuestionRepo.fetchSaveQuizQuestions(topicCode)
            .onSuccess { quizQuestions ->
                _state.update { quizState ->
                    quizState.copy(
                        questionsList = quizQuestions,
                        errorMessage = null,
                    )
                }
            }.onFailure { error ->
                _state.update { quizState ->
                    quizState.copy(
                        questionsList = emptyList(),
                        errorMessage = error.getErrorMessage(),
                    )
                }
            }


    }


}