package com.simbiri.myquiz.di

import com.simbiri.myquiz.data.remote.HttpClientFactory
import com.simbiri.myquiz.data.remote.KtorRemoteDataQuizSource
import com.simbiri.myquiz.data.remote.RemoteQuizDataSource
import com.simbiri.myquiz.data.repository.QuizQuestionRepoImpl
import com.simbiri.myquiz.data.repository.QuizTopicRepoImpl
import com.simbiri.myquiz.domain.repository.QuizQuestionRepository
import com.simbiri.myquiz.domain.repository.QuizTopicRepository
import com.simbiri.myquiz.presentation.dashboard.DashBoardViewModel
import com.simbiri.myquiz.presentation.quiz.QuizViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val koinModule= module {

    single<HttpClient>{ HttpClientFactory.create() }
    singleOf(::KtorRemoteDataQuizSource).bind<RemoteQuizDataSource>()

    singleOf(::QuizQuestionRepoImpl).bind<QuizQuestionRepository>()
    singleOf(::QuizTopicRepoImpl).bind<QuizTopicRepository>()

    viewModelOf(::QuizViewModel)
    viewModelOf(::DashBoardViewModel)
}