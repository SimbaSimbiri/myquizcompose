package com.simbiri.myquiz.presentation.result

import com.simbiri.myquiz.domain.model.QuizQuestion
import com.simbiri.myquiz.domain.model.UserAnswer

data class ResultState(
    val scorePercentage: Int = 0,
    val correctAnswers: Int= 0,
    val totalQuestions: Int = 0,
    val quizQuestions: List<QuizQuestion> =  emptyList(),
    val userAnswers: List<UserAnswer> = emptyList()
)
