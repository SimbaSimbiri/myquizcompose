package com.simbiri.myquiz.presentation.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simbiri.myquiz.domain.repository.QuizQuestionRepository
import com.simbiri.myquiz.domain.util.onFailure
import com.simbiri.myquiz.domain.util.onSuccess
import com.simbiri.myquiz.presentation.util.getErrorMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResultViewModel(
    private val questionRepository: QuizQuestionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ResultState())
    val state = _state.asStateFlow()


    private val _event = Channel<ResultEvent>()
    val event = _event.receiveAsFlow()

    init {
        fetchQuizMetrics()
    }

    private fun fetchQuizMetrics() {
        viewModelScope.launch {
            getQuizQuestions()
            getUserAnswers()
            updateResult()
        }
    }

    private suspend fun getQuizQuestions() {
        questionRepository.getQuizQuestions()
            .onSuccess { questions ->
                _state.update { it.copy(quizQuestions = questions) }

            }
            .onFailure { error ->
                _event.send(ResultEvent.ShowToast(error.getErrorMessage()))
            }

    }

    private suspend fun getUserAnswers() {
        questionRepository.getUserAnswers()
            .onSuccess { answers ->
                _state.update { it.copy(userAnswers = answers) }
            }
            .onFailure { error ->
                _event.send(ResultEvent.ShowToast(error.getErrorMessage()))
            }
    }


    private fun updateResult() {
        val quizQuestions = state.value.quizQuestions
        val userAnswers = state.value.userAnswers

        val totalQuestions = quizQuestions.size
        val correctAnswerCount = userAnswers.count { userAnswer ->
            val question = quizQuestions.find { it.id == userAnswer.questionId }
            question?.correctAnswer == userAnswer.selectedOption
        }
        val percentage = if (totalQuestions > 0) {
            (correctAnswerCount * 100) / quizQuestions.size
        } else {
            0
        }
        _state.update {
            it.copy(
                totalQuestions = totalQuestions,
                correctAnswerCount = correctAnswerCount,
                scorePercentage = percentage,
            )
        }
    }

}