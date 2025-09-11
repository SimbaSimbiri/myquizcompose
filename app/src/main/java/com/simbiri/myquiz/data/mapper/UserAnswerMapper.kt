package com.simbiri.myquiz.data.mapper

import com.simbiri.myquiz.data.local.entity.UserAnswerEntity
import com.simbiri.myquiz.domain.model.UserAnswer

private fun UserAnswer.toUserAnswerEntity() = UserAnswerEntity(
    questionId = questionId,
    selectedOption= selectedOption
)

fun List<UserAnswer>.toUserAnswerEntities() = map { it.toUserAnswerEntity() }

private fun UserAnswerEntity.entityToUserAnswer() = UserAnswer(
    questionId= questionId,
    selectedOption= selectedOption
)

fun List<UserAnswerEntity>.entityToUserAnswers() = map { it.entityToUserAnswer() }