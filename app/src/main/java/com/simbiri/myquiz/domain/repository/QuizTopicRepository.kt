package com.simbiri.myquiz.domain.repository

import com.simbiri.myquiz.domain.model.QuizTopic

interface QuizTopicRepository {
    suspend fun getQuizTopics(): List<QuizTopic>?
}