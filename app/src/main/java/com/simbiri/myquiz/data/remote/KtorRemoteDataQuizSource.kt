package com.simbiri.myquiz.data.remote

import com.simbiri.myquiz.data.remote.dto.QuizQuestionDto
import com.simbiri.myquiz.data.remote.dto.QuizTopicDto
import com.simbiri.myquiz.data.util.Constants.BASE_URL
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.ResultType
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.get
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.UnresolvedAddressException
import java.net.UnknownHostException

class KtorRemoteDataQuizSource(
    private val httpClient: HttpClient
): RemoteQuizDataSource {

    override suspend fun getQuizTopics(): ResultType<List<QuizTopicDto>, DataError>{

        return safeCall<List<QuizTopicDto>>{
            httpClient.get("$BASE_URL/quiz/topics")
        }
    }

    override suspend fun getQuizQuestions(): ResultType<List<QuizQuestionDto>, DataError>{

        return safeCall<List<QuizQuestionDto>>{
            httpClient.get("$BASE_URL/quiz/questions/random")
        }
    }


}