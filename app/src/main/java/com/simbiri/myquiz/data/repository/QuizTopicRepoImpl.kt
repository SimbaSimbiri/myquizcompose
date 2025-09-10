package com.simbiri.myquiz.data.repository

import com.simbiri.myquiz.data.local.dao.QuizTopicDao
import com.simbiri.myquiz.data.mapper.entityToQuizTopics
import com.simbiri.myquiz.data.mapper.toQuizTopics
import com.simbiri.myquiz.data.mapper.toQuizTopicsEntities
import com.simbiri.myquiz.data.remote.RemoteDataSource
import com.simbiri.myquiz.domain.model.QuizTopic
import com.simbiri.myquiz.domain.repository.QuizTopicRepository
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.ResultType

class QuizTopicRepoImpl(
    private val remoteDataSource: RemoteDataSource,
    private val topicDao: QuizTopicDao
) : QuizTopicRepository {

    override suspend fun getQuizTopics(): ResultType<List<QuizTopic>, DataError> {
        // the list can only be null if there is no internet, we fallback to the cached data in db
        val resultType = remoteDataSource.getQuizTopics()

        return when(resultType){
            is ResultType.Success -> {
                val quizTopicsDtoList = resultType.data
                topicDao.clearQuizTopics()
                topicDao.insertQuizTopics(topics = quizTopicsDtoList.toQuizTopicsEntities())
                ResultType.Success(quizTopicsDtoList.toQuizTopics())
            }
            is ResultType.Failure -> {
                val cachedTopics = topicDao.getAllQuizTopics()
                if (cachedTopics.isNotEmpty()){
                    ResultType.Success(cachedTopics.entityToQuizTopics())
                }else{
                    resultType
                }
            }
        }

    }
}