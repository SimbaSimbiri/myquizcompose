package com.simbiri.myquiz.data.repository

import com.simbiri.myquiz.data.local.dao.QuizQuestionDao
import com.simbiri.myquiz.data.local.dao.UserAnswerDao
import com.simbiri.myquiz.data.mapper.entityToQuizQuestion
import com.simbiri.myquiz.data.mapper.entityToQuizQuestions
import com.simbiri.myquiz.data.mapper.entityToUserAnswers
import com.simbiri.myquiz.data.mapper.toQuizQuestionEntities
import com.simbiri.myquiz.data.mapper.toQuizQuestions
import com.simbiri.myquiz.data.mapper.toUserAnswerEntities
import com.simbiri.myquiz.data.remote.RemoteDataSource
import com.simbiri.myquiz.domain.model.QuizQuestion
import com.simbiri.myquiz.domain.model.UserAnswer
import com.simbiri.myquiz.domain.repository.QuizQuestionRepository
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.ResultType

class QuizQuestionRepoImpl(
    private val remoteDataSource: RemoteDataSource,
    private val questionDao: QuizQuestionDao,
    private val userAnswerDao: UserAnswerDao
): QuizQuestionRepository {

    override suspend fun fetchSaveQuizQuestions(topicCode: Int): ResultType<List<QuizQuestion>, DataError>{
        val resultType = remoteDataSource.getQuizQuestions(topicCode)
        // the list can only be null if there is no internet, we fallback to the cached data in db

        return when(resultType){
            is ResultType.Success -> {
                val quizQuestionsDtoList = resultType.data
                userAnswerDao.clearAllUserAnswers()
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

    override suspend fun saveUserAnswers(userAnswers: List<UserAnswer>): ResultType<Unit, DataError> {
        return try{
            val answerEntities = userAnswers.toUserAnswerEntities()
            userAnswerDao.insertUserAnswers(answerEntities)
            ResultType.Success(Unit)
        } catch (e: Exception){
            ResultType.Failure(DataError.Unknown(e.message))
        }
    }

    override suspend fun getUserAnswers(): ResultType<List<UserAnswer>, DataError> {
        return try{
            val answerEntities = userAnswerDao.getAllUserAnswers()
            if (answerEntities.isNotEmpty()){
                ResultType.Success(answerEntities.entityToUserAnswers())
            } else{
                ResultType.Failure(DataError.Unknown("No user answers found from db"))
            }

        } catch (e: Exception){
            ResultType.Failure(DataError.Unknown(e.message))
        }
    }

    override suspend fun getQuizQuestionById(questionId: String): ResultType<QuizQuestion, DataError> {
        return try{
            val questionEntity = questionDao.getQuizQuestionById(questionId)
            if (questionEntity != null){
                ResultType.Success(questionEntity.entityToQuizQuestion())
            } else{
                ResultType.Failure(DataError.Unknown("No question found from db"))
            }

        } catch (e: Exception){
            ResultType.Failure(DataError.Unknown(e.message))
        }
    }

}