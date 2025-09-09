package com.simbiri.myquiz.data.local

import android.content.Context
import androidx.room.Room
import com.simbiri.myquiz.data.util.Constants.DATABASE

object DatabaseFactory {
    fun create(context: Context): QuizDatabase {
        return Room.databaseBuilder(
            klass = QuizDatabase::class.java,
            context = context.applicationContext,
            name = DATABASE
        )
        .fallbackToDestructiveMigration() // if we change schema/definition of entities, we will only save the new data
        .build()
    }
}