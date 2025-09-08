package com.simbiri.myquiz.data.repository

import com.simbiri.myquiz.data.mapper.toQuizTopics
import com.simbiri.myquiz.data.remote.RemoteQuizDataSource
import com.simbiri.myquiz.domain.model.QuizTopic
import com.simbiri.myquiz.domain.repository.QuizTopicRepository

class QuizTopicRepoImpl(
    private val remoteDataSource: RemoteQuizDataSource): QuizTopicRepository {

    override suspend fun getQuizTopics(): List<QuizTopic>? {
        val quizTopicsDtoList = remoteDataSource.getQuizTopics()
        return quizTopicsDtoList?.toQuizTopics()
    }
}