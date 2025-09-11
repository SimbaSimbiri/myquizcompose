package com.simbiri.myquiz.presentation.quiz

sealed interface QuizEvent {
    data class ShowToast(val message: String): QuizEvent
    data object NavigateToDashBoardScreen: QuizEvent
    data object NavigateToResultScreen: QuizEvent
}