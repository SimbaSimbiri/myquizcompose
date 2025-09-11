package com.simbiri.myquiz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.simbiri.myquiz.data.util.Constants.USER_ANSWER_TABLE

@Entity(tableName = USER_ANSWER_TABLE)
data class UserAnswerEntity(
    @PrimaryKey
    val questionId: String,
    val selectedOption: String
)
