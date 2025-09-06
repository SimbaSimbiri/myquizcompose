package com.simbiri.myquiz.presentation.quiz.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ExitQuizDialog(
    modifier: Modifier= Modifier,
    isDialogOpen: Boolean,
    title: String = "Exit Quiz?",
    confirmText: String = "Confirm",
    dismissText: String = "Cancel",
    onDialogDismiss: () -> Unit,
    onConfirmButtonClick: () -> Unit,
){
    if (isDialogOpen) {

        AlertDialog(
            modifier = modifier,
            title = { Text(text = title) },
            text = { Text(text= "Are you sure?\nProgress will not be saved.") },
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
fun ExitQuizDialogPreview(){
    ExitQuizDialog(
        isDialogOpen = true,
        onDialogDismiss = {},
        onConfirmButtonClick = {},
    )
}