package com.simbiri.myquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.simbiri.myquiz.data.local.entity.UserAnswerEntity

@Dao
interface UserAnswerDao {
    @Query("SELECT * FROM user_answer")
    suspend fun getAllUserAnswers(): List<UserAnswerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAnswers(userAnswers: List<UserAnswerEntity>)

    @Query("DELETE FROM user_answer")
    suspend fun clearAllUserAnswers()
}