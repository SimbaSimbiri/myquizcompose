package com.simbiri.myquiz.presentation.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.simbiri.myquiz.data.repository.QuizQuestionRepoImpl
import com.simbiri.myquiz.domain.model.UserAnswer
import com.simbiri.myquiz.presentation.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel: ViewModel() {

    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    private val quizQuestionRepo = QuizQuestionRepoImpl()
/*
    private val savedStateHandle = SavedStateHandle()
    private val route : Route.QuizScreen = savedStateHandle.toRoute<Route.QuizScreen>()

    private val topicCode: Int = route.topicCode*/

    init {
        getQuizQuestions()
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

            is QuizAction.onOptionSelected -> {

                val newAnswer = UserAnswer(action.questionId, action.answer)
                val currAnswers =  state.value.chosenAnswers.toMutableList()

                val idx = currAnswers.indexOfFirst { it.questionId == action.questionId }

                if (idx != -1) currAnswers[idx] = newAnswer
                else currAnswers.add(newAnswer)

                currAnswers.add(newAnswer)

                _state.update { it.copy(chosenAnswers = currAnswers) }
            }
        }

    }

    private fun getQuizQuestions(){

        viewModelScope.launch {
            val quizQuestions = quizQuestionRepo.getQuizQuestions()
            if (quizQuestions != null) {
                _state.update { quizState ->
                    quizState.copy(questionsList = quizQuestions)
                }
            }
        }

    }


}