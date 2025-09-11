package com.simbiri.myquiz.data.mapper

import com.simbiri.myquiz.data.local.entity.QuizQuestionEntity
import com.simbiri.myquiz.data.remote.dto.QuizQuestionDto
import com.simbiri.myquiz.domain.model.QuizQuestion

// DTO to Domain mappers
private fun QuizQuestionDto.toQuizQuestion() = QuizQuestion(
    id= id,
    question= question,
    correctAnswer= correctAnswer,
    allOptions =  (incorrectAnswers + correctAnswer).shuffled(),
    explanation= explanation,
    topicCode= topicCode
)

fun List<QuizQuestionDto>.toQuizQuestions()= map { it.toQuizQuestion() }

// DTO to Entity mappers
private fun QuizQuestionDto.toQuizQuestionEntity() = QuizQuestionEntity(
    id = id,
    question = question,
    correctAnswer = correctAnswer,
    incorrectAnswers = incorrectAnswers,
    explanation = explanation,
    topicCode = topicCode
)

fun List<QuizQuestionDto>.toQuizQuestionEntities() = map { it.toQuizQuestionEntity() }


// Entity to Domain mappers
fun QuizQuestionEntity.entityToQuizQuestion() = QuizQuestion(
    id = id,
    question = question,
    correctAnswer = correctAnswer,
    allOptions = (incorrectAnswers + correctAnswer).shuffled(),
    explanation = explanation,
    topicCode = topicCode
)
fun List<QuizQuestionEntity>.entityToQuizQuestions() = map { it.entityToQuizQuestion() }