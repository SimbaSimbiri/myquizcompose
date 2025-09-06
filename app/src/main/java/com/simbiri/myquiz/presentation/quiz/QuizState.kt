package com.simbiri.myquiz.presentation.quiz

import com.simbiri.myquiz.domain.QuizQuestion
import com.simbiri.myquiz.domain.UserAnswer

data class QuizState(
    val questionsList: List<QuizQuestion> = emptyList(),
    val chosenAnswers: List<UserAnswer> = emptyList(),
    val currentQuestionIdx: Int = 0,
    val errorMessage: String? = null,
    val topBarTitle: String = "Quiz"
)
