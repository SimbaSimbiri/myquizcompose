package com.simbiri.myquiz.presentation.quiz.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SubmitQuizDialog(
    modifier: Modifier= Modifier,
    isDialogOpen: Boolean,
    title: String = "Submit Quiz?",
    confirmText: String = "Submit",
    dismissText: String = "Cancel",
    onDialogDismiss: () -> Unit,
    onConfirmButtonClick: () -> Unit,
){
    if (isDialogOpen) {

        AlertDialog(
            modifier = modifier,
            title = { Text(text = title) },
            text = { Text(text= "Are you sure you want to submit?") },
            onDismissRequest = onDialogDismiss,
            confirmButton = {
                TextButton(onClick = onConfirmButtonClick) {
                    Text(text = confirmText)
                }
            },
            dismissButton = {
                TextButton(onClick = onDialogDismiss) {
                    Text(text = dismissText)
                }
            }
        )
    }
}


@Preview
@Composable
fun SubmitQuizDialogPreview(){
    SubmitQuizDialog(
        isDialogOpen = true,
        onDialogDismiss = {},
        onConfirmButtonClick = {},
    )
}