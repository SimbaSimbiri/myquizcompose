package com.simbiri.myquiz.data.repository

import com.simbiri.myquiz.data.mapper.toQuizQuestions
import com.simbiri.myquiz.data.remote.RemoteDataSource
import com.simbiri.myquiz.domain.model.QuizQuestion
import com.simbiri.myquiz.domain.repository.QuizQuestionRepository
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.ResultType

class QuizQuestionRepoImpl(
    private val remoteDataSource: RemoteDataSource,
): QuizQuestionRepository {

    override suspend fun getQuizQuestions(): ResultType<List<QuizQuestion>, DataError>{
        val resultType = remoteDataSource.getQuizQuestions()
        // the list can only be null if there is no internet, we fallback to the cached data in db

        return when(resultType){
            is ResultType.Success -> {
                val quizQuestionsDtoList = resultType.data
                ResultType.Success(quizQuestionsDtoList.toQuizQuestions())
            }
            is ResultType.Failure -> {
                resultType
            }
        }

    }

}