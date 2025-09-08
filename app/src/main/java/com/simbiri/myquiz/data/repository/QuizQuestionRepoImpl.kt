package com.simbiri.myquiz.data.repository

import com.simbiri.myquiz.data.mapper.toQuizQuestions
import com.simbiri.myquiz.data.remote.HttpClientFactory
import com.simbiri.myquiz.data.remote.KtorRemoteDataQuizSource
import com.simbiri.myquiz.domain.model.QuizQuestion
import com.simbiri.myquiz.domain.repository.QuizQuestionRepository

class QuizQuestionRepoImpl: QuizQuestionRepository {

    private val httpClient = HttpClientFactory.create()
    private val remoteDataSource = KtorRemoteDataQuizSource(httpClient)

    override suspend fun getQuizQuestions(): List<QuizQuestion>? {
        val quizQuestionDtoList = remoteDataSource.getQuizQuestions()
        return quizQuestionDtoList?.toQuizQuestions()
    }

    override suspend fun getQuizQuestionsByTopic(topicCode: Int): List<QuizQuestion>? {
        val quizQuestionDtoList = remoteDataSource.getQuizQuestionsByTopic(topicCode)
        return quizQuestionDtoList?.toQuizQuestions()
    }
}