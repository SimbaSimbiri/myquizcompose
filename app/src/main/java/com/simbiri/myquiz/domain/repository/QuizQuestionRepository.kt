package com.simbiri.myquiz.domain.repository

import com.simbiri.myquiz.domain.model.QuizQuestion

interface QuizQuestionRepository {
    suspend fun getQuizQuestions(): List<QuizQuestion>?
    suspend fun getQuizQuestionsByTopic(topicCode: Int): List<QuizQuestion>?
}