package com.simbiri.myquiz.data.repository

import com.simbiri.myquiz.data.local.dao.QuizQuestionDao
import com.simbiri.myquiz.data.mapper.entityToQuizQuestions
import com.simbiri.myquiz.data.mapper.toQuizQuestionEntities
import com.simbiri.myquiz.data.mapper.toQuizQuestions
import com.simbiri.myquiz.data.remote.RemoteDataSource
import com.simbiri.myquiz.domain.model.QuizQuestion
import com.simbiri.myquiz.domain.repository.QuizQuestionRepository
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.ResultType

class QuizQuestionRepoImpl(
    private val remoteDataSource: RemoteDataSource,
    private val questionDao: QuizQuestionDao
): QuizQuestionRepository {

    override suspend fun fetchSaveQuizQuestions(topicCode: Int): ResultType<List<QuizQuestion>, DataError>{
        val resultType = remoteDataSource.getQuizQuestions(topicCode)
        // the list can only be null if there is no internet, we fallback to the cached data in db

        return when(resultType){
            is ResultType.Success -> {
                val quizQuestionsDtoList = resultType.data
                questionDao.clearQuizQuestions()
                questionDao.insertQuizQuestions(quizQuestionsDtoList.toQuizQuestionEntities())
                ResultType.Success(quizQuestionsDtoList.toQuizQuestions())
            }
            is ResultType.Failure -> {
                resultType
            }
        }

    }

    override suspend fun getQuizQuestions(): ResultType<List<QuizQuestion>, DataError> {
        return try {
            val questionEntities = questionDao.getAllQuizQuestions()
            if (questionEntities.isNotEmpty()){
                ResultType.Success(questionEntities.entityToQuizQuestions())
            } else{
                ResultType.Failure(DataError.Unknown("No questions found from db"))
            }
        } catch (e: Exception){
            ResultType.Failure(DataError.Unknown(e.message))
        }
    }

}