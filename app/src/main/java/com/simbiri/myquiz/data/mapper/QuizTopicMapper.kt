package com.simbiri.myquiz.data.mapper

import com.simbiri.myquiz.data.remote.dto.QuizTopicDto
import com.simbiri.myquiz.data.util.Constants.BASE_URL
import com.simbiri.myquiz.domain.model.QuizTopic


fun QuizTopicDto.toQuizTopic() = QuizTopic(
    id= id,
    name= name,
    imageUrl= BASE_URL + imageUrl,
    code= code
)

fun List<QuizTopicDto>.toQuizTopics() = map { it.toQuizTopic() }