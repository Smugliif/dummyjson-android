package fi.tuni.dummyjsonusers

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class AddUserScreen: ViewModel() {
    @Composable
    fun Screen() {
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var newUser = User(
            firstName = firstName,
            lastName = lastName
        )
        val showDialog = remember { mutableStateOf(false) }

        Column {
            Header(displayText = "New User")
            Row() {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Surname") },
                    modifier = Modifier.weight(1f)
                )
            }
            Button(onClick = { viewModelScope.launch { showDialog.value = postUser(newUser) } },
                modifier = Modifier.fillMaxWidth().padding(20.dp)
            ) {
                Text("Save")
            }
            //TODO This looks horrible
            if (showDialog.value) {
                if (showDialog.value) {
                    AlertDialog(
                        onDismissRequest = {
                            showDialog.value = false
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
                                    showDialog.value = false
                                }
                            ) {
                                Text("Confirm")
                            }
                        }
                    )
                }
            }
        }
    }
}