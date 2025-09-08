package com.simbiri.myquiz.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simbiri.myquiz.data.repository.QuizQuestionRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel: ViewModel() {

    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    private val quizQuestionRepo = QuizQuestionRepoImpl()

    init {
        getQuizQuestions()
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