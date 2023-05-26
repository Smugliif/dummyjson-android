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
import kotlinx.coroutines.launch

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
        Button(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text("Save")
        }
        Spacer(modifier = Modifier.weight(2f))
    }
}