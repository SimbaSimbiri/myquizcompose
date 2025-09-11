package com.simbiri.myquiz.presentation.issue_report

import com.simbiri.myquiz.domain.model.QuizQuestion

data class IssueReportState(
    val quizQuestion: QuizQuestion? = null,
    val isQuestionCardExpanded: Boolean = false,
    val selectedIssueType: IssueType= IssueType.INCORRECT_ANSWER,
    val otherIssueText: String = "",
    val additionalComment: String = "",
    val emailForFollowUp: String = ""
)

enum class IssueType (val text: String){
    INCORRECT_ANSWER(text="Incorrect Answer"),
    UNCLEAR_QUESTION(text="Questions is vaguely phrased"),
    TYPO_OR_GRAMMAR(text="Typo or Grammatical mistake"),
    OTHER(text="Other")
}