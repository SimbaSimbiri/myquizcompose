package com.simbiri.myquiz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.simbiri.myquiz.data.local.converter.OptionListConverter
import com.simbiri.myquiz.data.local.dao.QuizQuestionDao
import com.simbiri.myquiz.data.local.dao.QuizTopicDao
import com.simbiri.myquiz.data.local.entity.QuizQuestionEntity
import com.simbiri.myquiz.data.local.entity.QuizTopicEntity

@Database(
    version = 2,
    entities = [
        QuizTopicEntity::class,
        QuizQuestionEntity::class
    ] // the list of tables that we expect our database to have
)
@TypeConverters(OptionListConverter::class)
abstract class QuizDatabase: RoomDatabase() {
    // each item in the list of entities should have its corresponding data access object
    abstract fun quizTopicDao(): QuizTopicDao
    abstract fun quizQuestionDao(): QuizQuestionDao
}