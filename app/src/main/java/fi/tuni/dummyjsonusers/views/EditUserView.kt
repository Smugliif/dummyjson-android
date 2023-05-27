package fi.tuni.dummyjsonusers.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fi.tuni.dummyjsonusers.api.putUser
import fi.tuni.dummyjsonusers.composables.BackArrow
import fi.tuni.dummyjsonusers.composables.Header
import fi.tuni.dummyjsonusers.dataclasses.User

@Composable
fun EditUserView(user: User, navController: NavController) {
    var firstName by remember { mutableStateOf("${user.firstName}") }
    var lastName by remember { mutableStateOf("${user.lastName}") }
    var isSuccess by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    Column() {
        Header(displayText = "Edit User")
        BackArrow(navController = navController)
        Spacer(modifier = Modifier.weight(1f))
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
            onClick = {
                val editedUser = User(
                    id = user.id,
                    firstName = firstName,
                    lastName = lastName
                )
                putUser(editedUser,
                    onSuccess = {
                        isSuccess = true
                        isError = false
                    },
                    onFailure = {
                        isError = true
                        isSuccess = false
                    })
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text("Save")
        }
        if (isSuccess)
            Text(text = "Saved successfully!", modifier = Modifier.align(CenterHorizontally))

        if (isError)
            Text(text = "Something went wrong.", modifier = Modifier.align(CenterHorizontally))
        Spacer(modifier = Modifier.weight(2f))
    }
}