package com.simbiri.myquiz.data.remote

import com.simbiri.myquiz.data.remote.dto.QuizQuestionDto
import com.simbiri.myquiz.data.remote.dto.QuizTopicDto

interface RemoteQuizDataSource {
    suspend fun getQuizTopics(): List<QuizTopicDto>?
    suspend fun getQuizQuestions(): List<QuizQuestionDto>?
}