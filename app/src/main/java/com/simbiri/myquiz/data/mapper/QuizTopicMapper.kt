package com.simbiri.myquiz.data.mapper

import com.simbiri.myquiz.data.local.entity.QuizTopicEntity
import com.simbiri.myquiz.data.remote.dto.QuizTopicDto
import com.simbiri.myquiz.data.util.Constants.BASE_URL
import com.simbiri.myquiz.domain.model.QuizTopic


private fun QuizTopicDto.toQuizTopic() = QuizTopic(
    id= id,
    name= name,
    imageUrl= BASE_URL + imageUrl,
    code= code
)


private fun QuizTopicDto.toQuizTopicEntity() = QuizTopicEntity(
    id = id,
    name = name,
    imageUrl = BASE_URL + imageUrl,
    code = code
)

private fun QuizTopicEntity.entityToQuizTopic() = QuizTopic(
    id = id,
    name = name,
    imageUrl = imageUrl, // since we already saved onto the database the full url
    code = code
)

fun List<QuizTopicDto>.toQuizTopics() = map { it.toQuizTopic() }
fun List<QuizTopicDto>.toQuizTopicsEntities() = map { it.toQuizTopicEntity() }
fun List<QuizTopicEntity>.entityToQuizTopics() = map { it.entityToQuizTopic() }