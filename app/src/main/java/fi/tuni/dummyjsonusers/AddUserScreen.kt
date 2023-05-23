package fi.tuni.dummyjsonusers

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*

@Composable
fun AddUserScreen() {
    var name by remember { mutableStateOf("") }
    Column {
        Header(displayText = "Add Users")
        Text(text = name)
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        Button(onClick = { postUser() }) {
            Text("Add user")
        }
    }
}