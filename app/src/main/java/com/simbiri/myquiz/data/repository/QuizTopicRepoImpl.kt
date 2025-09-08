package com.simbiri.myquiz.data.repository

import com.simbiri.myquiz.data.mapper.toQuizTopics
import com.simbiri.myquiz.data.remote.HttpClientFactory
import com.simbiri.myquiz.data.remote.KtorRemoteDataQuizSource
import com.simbiri.myquiz.domain.model.QuizTopic
import com.simbiri.myquiz.domain.repository.QuizTopicRepository

class QuizTopicRepoImpl: QuizTopicRepository {
    private val httpClient = HttpClientFactory.create()
    private val remoteDataSource = KtorRemoteDataQuizSource(httpClient)

    override suspend fun getQuizTopics(): List<QuizTopic>? {
        val quizTopicsDtoList = remoteDataSource.getQuizTopics()
        return quizTopicsDtoList?.toQuizTopics()
    }
}