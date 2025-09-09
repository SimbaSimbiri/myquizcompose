package com.simbiri.myquiz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.simbiri.myquiz.data.local.dao.QuizTopicDao
import com.simbiri.myquiz.data.local.entity.QuizTopicEntity

@Database(
    version = 1,
    entities = [QuizTopicEntity::class]
)
abstract class QuizDatabase: RoomDatabase() {
    abstract fun quizTopicDao(): QuizTopicDao
}