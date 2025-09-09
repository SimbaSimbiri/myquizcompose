package com.simbiri.myquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.simbiri.myquiz.data.local.entity.QuizTopicEntity

@Dao
interface QuizTopicDao {
    @Query("SELECT * FROM quiz_topic")
    suspend fun getAllQuizTopics(): List<QuizTopicEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizTopics(topics: List<QuizTopicEntity>)

    @Query("DELETE FROM quiz_topic")
    suspend fun clearQuizTopics()

    @Query("SELECT * FROM quiz_topic WHERE code = :topicCode")
    suspend fun getQuizTopicByCode(topicCode: Int): QuizTopicEntity?

}