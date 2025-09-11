package com.simbiri.myquiz.data.remote

import com.simbiri.myquiz.data.remote.dto.QuizQuestionDto
import com.simbiri.myquiz.data.remote.dto.QuizTopicDto
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.ResultType

interface RemoteDataSource {
    // interface with all the REST API calls

    suspend fun getQuizTopics(): ResultType<List<QuizTopicDto>, DataError>
    suspend fun getQuizQuestions(topicCode: Int): ResultType<List<QuizQuestionDto>, DataError>
}