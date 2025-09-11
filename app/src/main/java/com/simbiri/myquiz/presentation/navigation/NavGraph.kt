package com.simbiri.myquiz.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.simbiri.myquiz.presentation.dashboard.DashBoardScreen
import com.simbiri.myquiz.presentation.dashboard.DashBoardViewModel
import com.simbiri.myquiz.presentation.issue_report.IssueReportScreen
import com.simbiri.myquiz.presentation.issue_report.IssueReportViewModel
import com.simbiri.myquiz.presentation.quiz.QuizScreen
import com.simbiri.myquiz.presentation.quiz.QuizViewModel
import com.simbiri.myquiz.presentation.result.ResultScreen
import com.simbiri.myquiz.presentation.result.ResultViewModel
import org.koin.androidx.compose.koinViewModel

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

        composable<Route.DashboardScreen>{
            val viewModel = koinViewModel<DashBoardViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            DashBoardScreen(
                state = state,
                onTopicCardClick = { topicCode->
                    navController.navigate(Route.QuizScreen(topicCode))
                }
            )
        }
        composable<Route.QuizScreen>{
            val viewModel = koinViewModel<QuizViewModel>()
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
                onAction = viewModel::onAction,
                event = viewModel.event
            )
        }
        composable<Route.ResultScreen>{
            val viewModel = koinViewModel<ResultViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            ResultScreen(
                state = state,
                event = viewModel.event,
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
            val viewModel = koinViewModel<IssueReportViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            IssueReportScreen(
                state = state,
                event= viewModel.event,
                onAction = viewModel::onAction,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}