package com.simbiri.myquiz.data.mapper

import com.simbiri.myquiz.data.remote.dto.QuizQuestionDto
import com.simbiri.myquiz.domain.model.QuizQuestion

// DTO to Domain mappers
fun QuizQuestionDto.toQuizQuestion()= QuizQuestion(
    id= id,
    question= question,
    correctAnswer= correctAnswer,
    allOptions =  (incorrectAnswers + correctAnswer).shuffled(),
    explanation= explanation,
    topicCode= topicCode
)

fun List<QuizQuestionDto>.toQuizQuestions()= map { it.toQuizQuestion() }

// DTO to Entity mappers


// Entity to Domain mappers