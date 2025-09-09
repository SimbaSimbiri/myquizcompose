package com.simbiri.myquiz.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simbiri.myquiz.domain.repository.QuizTopicRepository
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.onFailure
import com.simbiri.myquiz.domain.util.onSuccess
import com.simbiri.myquiz.presentation.util.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashBoardViewModel(
    private val quizTopicRepo: QuizTopicRepository
): ViewModel() {

    private val _state= MutableStateFlow(DashBoardState())
    val state = _state.asStateFlow()

    init {
        getQuizTopics()
    }

    private fun getQuizTopics() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
           quizTopicRepo.getQuizTopics()
               .onSuccess {
                   quizTopics ->
                   _state.update { dashBoardState ->
                       dashBoardState.copy(
                           quizTopics = quizTopics,
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

}