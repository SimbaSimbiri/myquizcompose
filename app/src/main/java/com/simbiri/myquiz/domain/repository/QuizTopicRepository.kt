package com.simbiri.myquiz.domain.repository

import com.simbiri.myquiz.domain.model.QuizTopic
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.ResultType

interface QuizTopicRepository {
    suspend fun getQuizTopics(): ResultType<List<QuizTopic>, DataError>
}