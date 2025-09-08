package com.simbiri.myquiz.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simbiri.myquiz.domain.repository.QuizTopicRepository
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
            val quizTopics = quizTopicRepo.getQuizTopics()

            if (quizTopics != null) {
                _state.update { dashBoardState ->
                    dashBoardState.copy(quizTopics = quizTopics)
                }
            }

        }

    }

}