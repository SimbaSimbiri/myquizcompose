package com.simbiri.myquiz.domain.repository

import com.simbiri.myquiz.domain.model.QuizQuestion
import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.ResultType

interface QuizQuestionRepository {
    suspend fun getQuizQuestions(): ResultType<List<QuizQuestion>, DataError>
}