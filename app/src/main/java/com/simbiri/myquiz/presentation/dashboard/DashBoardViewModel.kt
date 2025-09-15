package com.simbiri.myquiz.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simbiri.myquiz.domain.repository.QuizTopicRepository
import com.simbiri.myquiz.domain.repository.UserPreferencesRepository
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.onFailure
import com.simbiri.myquiz.domain.util.onSuccess
import com.simbiri.myquiz.presentation.util.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent

class DashBoardViewModel(
    private val quizTopicRepo: QuizTopicRepository,
    private val userPrefsRepo: UserPreferencesRepository
): ViewModel() {

    private val _state= MutableStateFlow(DashBoardState())
    val state = combine(
        _state,
        userPrefsRepo.getQuestionsAttempted(),
        userPrefsRepo.getCorrectAnswers(),
        userPrefsRepo.getUserName()
    ) { state, questionsAttempted, correctAnswers, userName ->
        state.copy(
            questionsAttempted = questionsAttempted,
            correctAnswers = correctAnswers,
            userName = userName
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    init {
        getQuizTopics()
    }

    fun onAction(action: DashboardAction){
        when(action){

            DashboardAction.NameEditIconClick -> {
                _state.update {
                    it.copy(
                        userNameTextFieldValue = state.value.userName,
                        isNameEditDialogOpen = true)
                }
            }
            DashboardAction.NameEditDialogConfirm -> {
                _state.update { it.copy(isNameEditDialogOpen = false) }
                saveUsername(state.value.userNameTextFieldValue)
            }
            DashboardAction.NameEditDialogDismiss -> {
                _state.update { it.copy(isNameEditDialogOpen = false) }
            }

            is DashboardAction.SetUserName ->{
                val userNameError = validateUsername(action.userName)
                _state.update { it.copy(
                    userNameTextFieldValue = action.userName,
                    userNameError = userNameError
                )
                }
            }

            DashboardAction.RefreshIconClick -> {
                getQuizTopics()
            }
        }
    }

    private fun getQuizTopics() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
           quizTopicRepo.getQuizTopics()
               .onSuccess {
                   quizTopics ->
                   _state.update { dashBoardState ->
                       dashBoardState.copy(
                           quizTopics = quizTopics.sortedBy { it.name },
                           errorMessage = null,
                           isLoading = false
                       )
                   }
               }
               .onFailure { error ->
                   _state.update {
                       it.copy(
                           quizTopics = emptyList(),
                           errorMessage = error.getErrorMessage(),
                           isLoading = false
                       )
                   }
               }

        }

    }

    private fun saveUsername(username: String) {
        viewModelScope.launch {
            val trimmedUsername = username.trim()
            userPrefsRepo.saveUserName(trimmedUsername)
        }
    }

    private fun validateUsername(username: String): String? {
        return when {
            username.isBlank() -> "Please enter your name."
            username.length < 3 -> "Name is too short."
            username.length > 20 -> "Name is too long."
            else -> null
        }
    }

}