package com.simbiri.myquiz.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.simbiri.myquiz.data.local.DataStoreFactory
import com.simbiri.myquiz.data.local.DatabaseFactory
import com.simbiri.myquiz.data.local.QuizDatabase
import com.simbiri.myquiz.data.local.dao.QuizQuestionDao
import com.simbiri.myquiz.data.local.dao.QuizTopicDao
import com.simbiri.myquiz.data.local.dao.UserAnswerDao
import com.simbiri.myquiz.data.remote.HttpClientFactory
import com.simbiri.myquiz.data.remote.KtorRemoteDataSource
import com.simbiri.myquiz.data.remote.RemoteDataSource
import com.simbiri.myquiz.data.repository.IssueReportImpl
import com.simbiri.myquiz.data.repository.QuizQuestionRepoImpl
import com.simbiri.myquiz.data.repository.QuizTopicRepoImpl
import com.simbiri.myquiz.data.repository.UserPreferencesRepoImpl
import com.simbiri.myquiz.domain.repository.IssueReportRepository
import com.simbiri.myquiz.domain.repository.QuizQuestionRepository
import com.simbiri.myquiz.domain.repository.QuizTopicRepository
import com.simbiri.myquiz.domain.repository.UserPreferencesRepository
import com.simbiri.myquiz.presentation.dashboard.DashBoardViewModel
import com.simbiri.myquiz.presentation.issue_report.IssueReportViewModel
import com.simbiri.myquiz.presentation.quiz.QuizViewModel
import com.simbiri.myquiz.presentation.result.ResultViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val koinModule= module {
    // we create the single instance of our okhttp client and the service/ remote source getting
    // data from the ktor server
    single<HttpClient>{ HttpClientFactory.create() }
    singleOf(::KtorRemoteDataSource).bind<RemoteDataSource>()

    // database and its dao for every entity
    single<QuizDatabase>{ DatabaseFactory.create(get()) }
    single<QuizTopicDao>{ get<QuizDatabase>().quizTopicDao()}
    single<QuizQuestionDao>{ get<QuizDatabase>().quizQuestionDao()}
    single<UserAnswerDao>{ get<QuizDatabase>().userAnswerDao()}

    // preferences data store
    single<DataStore<Preferences>>{ DataStoreFactory.create(get()) }

    // repositories
    singleOf(::QuizQuestionRepoImpl).bind<QuizQuestionRepository>()
    singleOf(::QuizTopicRepoImpl).bind<QuizTopicRepository>()
    singleOf(::IssueReportImpl).bind<IssueReportRepository>()
    singleOf(::UserPreferencesRepoImpl).bind<UserPreferencesRepository>()

    // view models
    viewModelOf(::QuizViewModel)
    viewModelOf(::DashBoardViewModel)
    viewModelOf(::ResultViewModel)
    viewModelOf(::IssueReportViewModel)
}