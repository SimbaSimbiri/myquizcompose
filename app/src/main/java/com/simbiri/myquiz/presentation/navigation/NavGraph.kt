package com.simbiri.myquiz.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.simbiri.myquiz.domain.model.QuizQuestion
import com.simbiri.myquiz.presentation.dashboard.DashBoardScreen
import com.simbiri.myquiz.presentation.dashboard.DashBoardViewModel
import com.simbiri.myquiz.presentation.issue_report.IssueReportScreen
import com.simbiri.myquiz.presentation.issue_report.IssueReportState
import com.simbiri.myquiz.presentation.quiz.QuizScreen
import com.simbiri.myquiz.presentation.quiz.QuizViewModel
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
            val viewModel = viewModel<DashBoardViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            DashBoardScreen(
                state = state,
                onTopicCardClick = { topicCode->
                    navController.navigate(Route.QuizScreen(topicCode))
                }
            )
        }
        composable<Route.QuizScreen>{ navBackStackEntry->
            val viewModel = viewModel<QuizViewModel>(viewModelStoreOwner = navBackStackEntry)
            val state by viewModel.state.collectAsStateWithLifecycle()

            QuizScreen(
                state = state,
                navigateToDashBoardScreen= {
                    navController.navigateUp()
                },
                navigateToResultScreen = {
                    navController.navigate(Route.ResultScreen){
                        popUpTo<Route.QuizScreen>{
                            inclusive= true
                        }
                    }
                },
                onAction = viewModel::onAction
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
        composable<Route.IssueReportScreen>{ navBackStackEntry->
            val questionId = navBackStackEntry.toRoute<Route.IssueReportScreen>().questionId
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