package fi.tuni.dummyjsonusers

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch

class AddUserView : ViewModel() {
    @Composable
    fun Screen(navController: NavController) {
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var newUser = User(
            firstName = firstName,
            lastName = lastName
        )
        val showDialog = remember { mutableStateOf(false) }
        Column {
            Header(displayText = "New User")
            BackArrow(navController = navController)
            Spacer(modifier = Modifier.weight(1f) )
            Row() {
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
                onClick = { viewModelScope.launch { showDialog.value = postUser(newUser) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
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
            Spacer(modifier = Modifier.weight(2f))
        }
    }
}