package com.simbiri.myquiz.presentation.issue_report

sealed interface IssueReportAction {
    data object ExpandQuestionCard : IssueReportAction
    data class SetIssueReportType(val issueType: IssueType): IssueReportAction
    data class SetOtherIssueText(val otherIssueText: String): IssueReportAction
    data class SetAdditionalComment(val additionalComment: String): IssueReportAction
    data class SetEmailForFollowUp(val emailForFollowUp: String): IssueReportAction
    data object SubmitReport : IssueReportAction

}