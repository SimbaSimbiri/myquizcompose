package com.simbiri.myquiz.domain

data class QuizQuestion(
    val id: String,
    val question: String,
    val correctAnswer: String,
    val allOptions: List<String>,
    val explanation: String,
    val topicCode:Int
)
