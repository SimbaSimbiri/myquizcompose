package com.simbiri.myquiz.data.remote

import com.simbiri.myquiz.data.remote.dto.IssueReportDto
import com.simbiri.myquiz.data.remote.dto.QuizQuestionDto
import com.simbiri.myquiz.data.remote.dto.QuizTopicDto
import com.simbiri.myquiz.data.util.Constants.BASE_URL
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.ResultType
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class KtorRemoteDataSource(
    private val httpClient: HttpClient
): RemoteDataSource {

    override suspend fun getQuizTopics(): ResultType<List<QuizTopicDto>, DataError>{

        return safeCall<List<QuizTopicDto>>{
            httpClient.get("$BASE_URL/quiz/topics")
        }
    }

    override suspend fun insertIssueReport(issueReportDto: IssueReportDto): ResultType<Unit, DataError> {
       return safeCall<Unit> {
           httpClient.post(urlString = "$BASE_URL/report/issues") {
               setBody(issueReportDto)
           }
       }
    }

    override suspend fun getQuizQuestions(topicCode: Int): ResultType<List<QuizQuestionDto>, DataError>{

        return safeCall<List<QuizQuestionDto>>{
            httpClient.get("$BASE_URL/quiz/questions/random"){
                parameter("topicCode", topicCode)
            }
        }
    }


}