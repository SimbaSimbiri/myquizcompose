package com.simbiri.myquiz.presentation.dashboard.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NameEditDialog(
    modifier: Modifier= Modifier,
    isDialogOpen: Boolean,
    textFieldValue: String,
    userNameError: String?,
    title: String = "Edit your name",
    confirmText: String = "Confirm",
    dismissText: String = "Cancel",
    onDialogDismiss: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    onTextFieldValueChange: (String) -> Unit

){
    if (isDialogOpen) {

        AlertDialog(
            modifier = modifier,
            title = { Text(text = title) },
            text = {
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = onTextFieldValueChange,
                    singleLine = true,
                    isError = userNameError != null && textFieldValue.isNotBlank(),
                    supportingText = {
                        Text(text = userNameError.orEmpty())
                    }
                )
            },
            onDismissRequest = onDialogDismiss,
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick,
                    enabled = userNameError == null
                ) {
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
fun NameEditDialogPreview(){
    NameEditDialog(
        isDialogOpen = true,
        textFieldValue = "wfbertbetrybeytbe5teyb",
        userNameError = "Name is too long",
        onDialogDismiss = {},
        onConfirmButtonClick = {},
        onTextFieldValueChange = {}
    )
}