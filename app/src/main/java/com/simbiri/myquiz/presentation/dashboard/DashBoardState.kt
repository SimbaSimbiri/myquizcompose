package com.simbiri.myquiz.presentation.dashboard

import com.simbiri.myquiz.domain.QuizTopic

data class DashBoardState(
    val userName : String = "Android Developer",
    val questionsAttempted: Int = 0,
    val correctAnswers: Int = 0,
    val quizTopics: List<QuizTopic> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
