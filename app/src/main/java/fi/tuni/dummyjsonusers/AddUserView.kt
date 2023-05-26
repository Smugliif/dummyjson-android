package fi.tuni.dummyjsonusers

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun AddUserView(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    val newUser = User(
        firstName = firstName,
        lastName = lastName
    )
    var showDialog by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    Column {
        Header(displayText = "New User")
        BackArrow(navController = navController)
        Spacer(modifier = Modifier.weight(1f))
        Row {
            OutlinedTextField(
                value = firstName,
                onValueChange = { if (it.length <= 50) firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { if (it.length <= 50) lastName = it },
                label = { Text("Surname") },
                modifier = Modifier.weight(1f)
            )
        }
        Button(
            onClick = {
                postUser(
                    newUser,
                    onSuccess = { showDialog = true },
                    onFailure = { isError = true })
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text("Save")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Text(text = "User Successfully Saved")
                },
                text = {
                    Text("User ${newUser.firstName} has been saved")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text("Confirm")
                    }
                }
            )
        }
        if (isError) {
            Text(
                text = "Something went wrong.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.weight(2f))
    }
}
