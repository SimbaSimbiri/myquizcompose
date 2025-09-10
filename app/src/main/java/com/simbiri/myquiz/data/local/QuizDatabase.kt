package com.simbiri.myquiz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.simbiri.myquiz.data.local.dao.QuizTopicDao
import com.simbiri.myquiz.data.local.entity.QuizTopicEntity

@Database(
    version = 1,
    entities = [QuizTopicEntity::class] // the list of tables that we expect our database to have
)
abstract class QuizDatabase: RoomDatabase() {
    // each item in the list of entities should have its corresponding data access object
    abstract fun quizTopicDao(): QuizTopicDao
}