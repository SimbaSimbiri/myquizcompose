package com.simbiri.myquiz.presentation.issue_report

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simbiri.myquiz.domain.QuizQuestion
import com.simbiri.myquiz.presentation.issue_report.component.IssueReportTopBar
import com.simbiri.myquiz.presentation.issue_report.component.QuestionCard

@Composable
fun IssueReportScreen(
    modifier: Modifier = Modifier,
    state: IssueReportState
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IssueReportTopBar(
            title = "Report an Issue",
            onBackButtonClick = {}
        )

        Column(
            modifier= Modifier
                .padding(horizontal = 10.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            QuestionCard(
                modifier = Modifier.fillMaxWidth(),
                question = state.quizQuestion,
                isCardExpanded = state.isQuestionCardExpanded,
                onExpandClick = {}
            )

            Spacer(modifier = Modifier.height(10.dp))

            IssueTypeSection(
                selectedIssueType = state.selectedIssueType,
                otherIssueText = state.otherIssueText,
                onOtherIssueTextChanged = {},
                onIssueTypeSelected = {}
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                value = state.additionalComment,
                onValueChange = { },
                label = { Text(text = "Additional Comment") },
                supportingText = {
                    Text(text = "Describe the issue in more detail (Optional)")
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.emailForFollowUp,
                onValueChange = { },
                label = { Text(text = "Email for follow up") },
                singleLine = true,
                supportingText = {
                    Text(text = "Provide valid email for feedback (Optional)")
                }
            )
        }

        Button(
            modifier= Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
            ,
            onClick = {}
        ) {
            Text(
                modifier= Modifier.padding(horizontal = 10.dp),
                text = "Submit Report")
        }


    }

}

@Composable
private fun IssueTypeSection(
    modifier: Modifier = Modifier,
    selectedIssueType: IssueType,
    otherIssueText: String,
    onOtherIssueTextChanged: (String) -> Unit,
    onIssueTypeSelected: (IssueType) -> Unit
    ) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Issue Type",
            style = MaterialTheme.typography.bodySmall
        )
        FlowRow {

            IssueType.entries.forEach { issue ->
                Row (
                    modifier= Modifier
                        .widthIn(250.dp)
                        .clickable { { onIssueTypeSelected(issue) } }
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = issue == selectedIssueType,
                        onClick = { onIssueTypeSelected(issue) }
                    )

                    if (issue == IssueType.OTHER) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = otherIssueText,
                            onValueChange = onOtherIssueTextChanged,
                            label = { Text(text = issue.text) },
                            singleLine = true,
                            enabled = selectedIssueType == IssueType.OTHER
                        )
                    } else Text(text = issue.text)
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun IssueReportScreenPreview() {
    IssueReportScreen(
        state = IssueReportState(
            quizQuestion = QuizQuestion(
                id = "1",
                question = "What is the capital of India?",
                correctAnswer = "New Delhi",
                allOptions = listOf("New Delhi", "Mumbai", "Kolkata", "Chennai"),
                explanation = "The capital of India is Mumbai.",
                topicCode = 1
            ),
            selectedIssueType = IssueType.INCORRECT_ANSWER,
        )
    )


}