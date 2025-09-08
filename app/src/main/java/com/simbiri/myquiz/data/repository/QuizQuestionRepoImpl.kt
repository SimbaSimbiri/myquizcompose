package com.simbiri.myquiz.data.repository

import com.simbiri.myquiz.data.mapper.toQuizQuestions
import com.simbiri.myquiz.data.remote.RemoteQuizDataSource
import com.simbiri.myquiz.domain.model.QuizQuestion
import com.simbiri.myquiz.domain.repository.QuizQuestionRepository

class QuizQuestionRepoImpl(
    private val remoteDataSource: RemoteQuizDataSource
): QuizQuestionRepository {

    override suspend fun getQuizQuestions(): List<QuizQuestion>? {
        val quizQuestionDtoList = remoteDataSource.getQuizQuestions()
        return quizQuestionDtoList?.toQuizQuestions()
    }

    override suspend fun getQuizQuestionsByTopic(topicCode: Int): List<QuizQuestion>? {
        val quizQuestionDtoList = remoteDataSource.getQuizQuestions()
        return quizQuestionDtoList?.toQuizQuestions()
    }
}