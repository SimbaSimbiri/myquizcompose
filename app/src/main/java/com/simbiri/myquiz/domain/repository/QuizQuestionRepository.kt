package com.simbiri.myquiz.domain.repository

import com.simbiri.myquiz.domain.model.QuizQuestion
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.ResultType

// this interface obscures the implementation of how we will get our model data of type QuizQuestion
// in short we the domain and UI end don't care how the data will be sourced, be it from Server or DB
// we also obscure the implementation of both to the data.repository layer
interface QuizQuestionRepository {
    suspend fun getQuizQuestions(): ResultType<List<QuizQuestion>, DataError>
}