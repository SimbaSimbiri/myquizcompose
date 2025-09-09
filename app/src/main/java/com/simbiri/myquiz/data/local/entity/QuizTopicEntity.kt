package com.simbiri.myquiz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.simbiri.myquiz.data.util.Constants.QUIZ_TOPIC_TABLE


@Entity(tableName = QUIZ_TOPIC_TABLE)
data class QuizTopicEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val imageUrl: String,
    val code: Int
)
