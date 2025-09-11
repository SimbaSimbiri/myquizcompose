package com.simbiri.myquiz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.simbiri.myquiz.data.util.Constants.QUIZ_QUESTION_TABLE

@Entity(tableName = QUIZ_QUESTION_TABLE)
data class QuizQuestionEntity(
    @PrimaryKey
    val id: String,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val explanation: String,
    val topicCode:Int
)
