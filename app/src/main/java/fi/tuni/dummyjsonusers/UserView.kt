package fi.tuni.dummyjsonusers

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import okhttp3.*
import java.io.IOException


@Composable
fun UserView(user: User, navController: NavController) {
    var showDeletePrompt by remember { mutableStateOf(false) }
    var isDeleted by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    Column() {
        Header("${user.firstName} ${user.lastName}")
        Row() {
            BackArrow(navController = navController)
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(onClick = { navController.navigate("editUserView/${user.id}") }) {
                Text(text = "Edit")
            }
            OutlinedButton(
                onClick = { showDeletePrompt = true },
                modifier = Modifier.padding(horizontal = 30.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                    contentDescription = "Delete"
                )
            }
        }
        if (showDeletePrompt) {
            AlertDialog(
                onDismissRequest = {
                    showDeletePrompt = false
                },
                title = {
                    Text(text = "Do you want to delete ${user.firstName}?")
                },
                text = {
                    Text("This action is irreversible!")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDeletePrompt = false
                            deleteUser(
                                id = user.id!!,
                                onSuccess = { isDeleted = true },
                                onFailure = { isError = true })
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDeletePrompt = false }) {
                        Text(text = "Cancel")
                    }
                }
            )
        }

        if (isDeleted) {
            AlertDialog(
                onDismissRequest = {
                    isDeleted = false
                    navController.popBackStack()
                },
                title = {
                    Text(text = "Deleted successfully")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            isDeleted = false
                            navController.popBackStack()
                        }
                    ) {
                        Text("Confirm")
                    }
                }
            )
        }

        if (isError) {
            Text(
                text = "Something went wrong, try again.",
                modifier = Modifier.align(CenterHorizontally)
            )
        }

    }
}

// Send DELETE request to API with target user's ID
fun deleteUser(id: Int, onSuccess: () -> Unit, onFailure: () -> Unit) {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://dummyjson.com/users/${id}")
        .delete()
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            onFailure()
        }

        override fun onResponse(call: Call, response: Response) {
            // Handle request success
            if (response.isSuccessful) {
                Log.d("DEBUG", "DELETE request successful")
                val json = response.body?.string()
                Log.d("DEBUG", json!!)
                onSuccess()
            } else {
                Log.d("DEBUG", "DELETE request failed")
                onFailure()
            }
            response.close()
        }
    })
}