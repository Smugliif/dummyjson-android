package fi.tuni.dummyjsonusers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun EditUserView(user: User, navController: NavController) {
    var editedUser by remember { mutableStateOf(User(
        id = user.id,
        firstName = user.firstName,
        lastName = user.lastName
    ))}
    Column() {
        Header(displayText = "Edit User")
        BackArrow(navController = navController)
        Spacer(modifier = Modifier.weight(1f) )
        Row() {
            OutlinedTextField(
                value = editedUser.firstName,
                onValueChange = { if (it.length <= 50) editedUser.firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = editedUser.lastName,
                onValueChange = { if (it.length <= 50) editedUser.lastName = it },
                label = { Text("Surname") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}