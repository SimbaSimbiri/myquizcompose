package com.simbiri.myquiz.presentation.quiz

sealed interface QuizAction {
    data object PrevQuestionButtonClick: QuizAction
    data object NextQuestionButtonClick: QuizAction
    data class JumpToQuestion(val questionIdx: Int): QuizAction
    data class OnOptionSelected(val questionId: String, val answer: String): QuizAction

    data object SubmitQuizButtonClick: QuizAction
    data object SubmitQuizDialogDismiss: QuizAction
    data object ConfirmSubmitQuizButtonClick: QuizAction

    data object ExitQuizButtonClick: QuizAction
    data object ExitQuizDialogDismiss: QuizAction
    data object ConfirmExitQuizButtonClick: QuizAction

    data object RefreshQuizButtonClick: QuizAction


}