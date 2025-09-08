package com.simbiri.myquiz.data.remote

import com.simbiri.myquiz.data.remote.dto.QuizQuestionDto
import com.simbiri.myquiz.data.remote.dto.QuizTopicDto
import com.simbiri.myquiz.data.util.Constants.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class KtorRemoteDataQuizSource(
    private val httpClient: HttpClient
){
    // class with all the functions that will call the API

    suspend fun getQuizTopics(): List<QuizTopicDto>?{
        return try {
            val response = httpClient.get("$BASE_URL/quiz/topics")
            response.body<List<QuizTopicDto>>()

        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    suspend fun getQuizQuestions(): List<QuizQuestionDto>?{
        return try {
            val response = httpClient.get("$BASE_URL/quiz/questions")
            response.body<List<QuizQuestionDto>>()

        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }


}