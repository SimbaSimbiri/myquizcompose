package com.simbiri.myquiz.data.remote

import com.simbiri.myquiz.data.remote.dto.QuizQuestionDto
import com.simbiri.myquiz.data.remote.dto.QuizTopicDto
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.ResultType

interface RemoteQuizDataSource {
    // interface with all the REST API calls

    suspend fun getQuizTopics(): ResultType<List<QuizTopicDto>, DataError>
    suspend fun getQuizQuestions(): ResultType<List<QuizQuestionDto>, DataError>
}