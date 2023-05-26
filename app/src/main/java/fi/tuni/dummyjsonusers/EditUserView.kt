package fi.tuni.dummyjsonusers

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EditUserView(user: User, navController: NavController) {
    var firstName by remember { mutableStateOf("$user.firstName") }
    var lastName by remember { mutableStateOf("$user.lastName") }

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
                putUser(editedUser)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text("Save")
        }
        Spacer(modifier = Modifier.weight(2f))
    }
}