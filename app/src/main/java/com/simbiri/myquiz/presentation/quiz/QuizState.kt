package com.simbiri.myquiz.presentation.quiz

import com.simbiri.myquiz.domain.model.QuizQuestion
import com.simbiri.myquiz.domain.model.UserAnswer

data class QuizState(
    val topBarTitle: String = "Quiz",

    val questionsList: List<QuizQuestion> = emptyList(),
    val chosenAnswers: List<UserAnswer> = emptyList(),
    val currentQuestionIdx: Int = 0,

    val isLoading: Boolean = false,
    val isSubmitQuizDialogOpen: Boolean = false,
    val isExitQuizDialogOpen: Boolean = false,

    val loadingMessage: String? = null,
    val errorMessage: String? = null,

)
