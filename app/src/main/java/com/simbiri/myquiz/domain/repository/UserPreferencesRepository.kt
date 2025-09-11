package com.simbiri.myquiz.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getQuestionsAttempted(): Flow<Int>

    fun getCorrectAnswers(): Flow<Int>

    fun getUserName(): Flow<String>

    suspend fun saveUserName(userName: String)

    suspend fun saveScore(questionsAttempted: Int, correctAnswers: Int)
}