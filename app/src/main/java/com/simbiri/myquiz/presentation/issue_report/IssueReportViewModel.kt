package com.simbiri.myquiz.presentation.issue_report

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.simbiri.myquiz.domain.model.IssueReport
import com.simbiri.myquiz.domain.repository.IssueReportRepository
import com.simbiri.myquiz.domain.repository.QuizQuestionRepository
import com.simbiri.myquiz.domain.util.onFailure
import com.simbiri.myquiz.domain.util.onSuccess
import com.simbiri.myquiz.presentation.navigation.Route
import com.simbiri.myquiz.presentation.util.getErrorMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IssueReportViewModel(
    private val questionRepository: QuizQuestionRepository,
    private val issueReportRepository: IssueReportRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(IssueReportState())
    val state = _state.asStateFlow()

    private val _event = Channel<IssueReportEvent>()
    val event = _event.receiveAsFlow()

    private val questionId = savedStateHandle.toRoute<Route.IssueReportScreen>().questionId

    init {
        getQuestionById(questionId)
    }

    private fun getQuestionById(questionId: String) {
        viewModelScope.launch {
            questionRepository.getQuizQuestionById(questionId)
                .onSuccess { question ->
                    _state.update { it.copy(quizQuestion = question) }
                }
                .onFailure { error ->
                    _event.send(IssueReportEvent.ShowToast(error.getErrorMessage()))
                }
        }
    }

    private fun submitReport() {
        viewModelScope.launch {
            val currentState = state.value

            val errorMessage = validateInputs(currentState)
            errorMessage?.let {
                _event.send(IssueReportEvent.ShowToast(it))
                return@launch
            }

            val issueType = if (currentState.selectedIssueType == IssueType.OTHER) {
                currentState.otherIssueText
            } else currentState.selectedIssueType.text

            val report = IssueReport(
                questionId = questionId,
                issueType = issueType,
                additionalComment = currentState.additionalComment.ifBlank { null },
                userEmail = currentState.emailForFollowUp.ifBlank { null },
                timeStampMillis = System.currentTimeMillis()
            )

            issueReportRepository.insertIssueReport(report)
                .onSuccess {
                    _event.send(IssueReportEvent.ShowToast("Report submitted successfully"))
                    _event.send(IssueReportEvent.NavigateUp)
                }
                .onFailure { error ->
                    _event.send(IssueReportEvent.ShowToast(error.getErrorMessage()))
                }
        }

    }
    private fun validateInputs(state: IssueReportState): String? {
        return when {
            state.selectedIssueType == IssueType.OTHER && state.otherIssueText.isBlank() -> {
                "Please add your custom issue type."
            }
            state.additionalComment.isNotBlank() && state.additionalComment.length < 5 -> {
                "Additional comment must be at least 5 character long"
            }
            state.emailForFollowUp.isNotBlank() && !state.emailForFollowUp.isValidEmail() -> {
                "Invalid Email address"
            }
            else -> null
        }
    }

    private fun String.isValidEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    fun onAction(action: IssueReportAction) {
        when (action) {
            is IssueReportAction.ExpandQuestionCard -> {
                _state.update { it.copy(isQuestionCardExpanded = !it.isQuestionCardExpanded) }
            }

            is IssueReportAction.SetIssueReportType -> {
                _state.update { it.copy(selectedIssueType = action.issueType) }
            }

            is IssueReportAction.SetOtherIssueText -> {
                _state.update { it.copy(otherIssueText = action.otherIssueText) }
            }

            is IssueReportAction.SetAdditionalComment -> {
                _state.update { it.copy(additionalComment = action.additionalComment) }

            }

            is IssueReportAction.SetEmailForFollowUp -> {
                _state.update { it.copy(emailForFollowUp = action.emailForFollowUp) }

            }

            IssueReportAction.SubmitReport -> {
                submitReport()
            }
        }

    }
}