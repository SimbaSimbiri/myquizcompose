package com.simbiri.myquiz.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.simbiri.myquiz.domain.QuizQuestion
import com.simbiri.myquiz.domain.QuizTopic
import com.simbiri.myquiz.presentation.dashboard.DashBoardScreen
import com.simbiri.myquiz.presentation.dashboard.DashBoardState
import com.simbiri.myquiz.presentation.issue_report.IssueReportScreen
import com.simbiri.myquiz.presentation.issue_report.IssueReportState
import com.simbiri.myquiz.presentation.quiz.QuizScreen
import com.simbiri.myquiz.presentation.quiz.QuizState
import com.simbiri.myquiz.presentation.result.ResultScreen
import com.simbiri.myquiz.presentation.result.ResultState

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {

    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = Route.DashboardScreen,
    ){
        val dummyQns = List(5) { idx ->
            QuizQuestion(
                id = "$idx",
                question = "What is the capital of India?",
                correctAnswer = "New Delhi",
                allOptions = listOf("New Delhi", "Mumbai", "Kolkata", "Chennai"),
                explanation = "The capital of India is New Delhi.",
                topicCode = 1
            )
        }
        composable<Route.DashboardScreen>{
            val dummyTopics = List(7) { index ->
                QuizTopic(
                    id = "Dummy",
                    name = "Ai and ML $index",
                    imageUrl = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAA",
                    code = index
                )
            }

            DashBoardScreen(
                state = DashBoardState(quizTopics = dummyTopics),
                onTopicCardClick = { topicCode->
                    navController.navigate(Route.QuizScreen(topicCode))
                }
            )
        }
        composable<Route.QuizScreen>{
            val topicCode = it.toRoute<Route.QuizScreen>().topicCode

            QuizScreen(
                state = QuizState(topBarTitle = "Topic $topicCode", questionsList = dummyQns),
                navigateToDashBoardScreen = {
                    navController.navigateUp() },
                navigateToResultScreen = {
                    navController.navigate(Route.ResultScreen){
                        popUpTo<Route.QuizScreen>{
                            inclusive= true
                        }
                    }
                }
            )
        }
        composable<Route.ResultScreen>{
            ResultScreen(
                state = ResultState(
                    quizQuestions = dummyQns,
                ),
                onReportClick = { questionId->
                    navController.navigate(Route.IssueReportScreen(questionId))
                },
                onStartNewQuizClick = {
                    navController.navigate(Route.DashboardScreen){
                        popUpTo<Route.ResultScreen>{
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Route.IssueReportScreen>{
            val questionId = it.toRoute<Route.IssueReportScreen>().questionId
            val question = dummyQns.find { it.id == questionId }
            IssueReportScreen(
                state = IssueReportState(quizQuestion = question),
                onBackButtonClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}