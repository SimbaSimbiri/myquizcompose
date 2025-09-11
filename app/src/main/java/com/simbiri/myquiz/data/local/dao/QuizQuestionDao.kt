package com.simbiri.myquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.simbiri.myquiz.data.local.entity.QuizQuestionEntity

@Dao
interface QuizQuestionDao {
    @Query("SELECT * FROM quiz_question")
    suspend fun getAllQuizQuestions(): List<QuizQuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizQuestions(quizQuestions: List<QuizQuestionEntity>)

    @Query("DELETE FROM quiz_question")
    suspend fun clearQuizQuestions()

    @Query("SELECT * FROM quiz_question WHERE id = :questionId")
    suspend fun getQuizQuestionById(questionId: String): QuizQuestionEntity

}