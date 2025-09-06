package com.simbiri.myquiz.presentation.issue_report.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueReportTopBar(
    modifier: Modifier = Modifier,
    title: String = "AndroidQuiz",
    onBackButtonClick: () -> Unit,
) {
    TopAppBar(
        modifier=modifier,
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Navigate back"
                )
            }
        }
    )
}

@Preview
@Composable
fun IssueReportTopBarPreview() {
    IssueReportTopBar(
        title = "Report an Issue",
        onBackButtonClick = {}
    )
}