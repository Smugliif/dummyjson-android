package fi.tuni.dummyjsonusers.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fi.tuni.dummyjsonusers.R
import fi.tuni.dummyjsonusers.api.deleteUser
import fi.tuni.dummyjsonusers.composables.BackArrow
import fi.tuni.dummyjsonusers.composables.Header
import fi.tuni.dummyjsonusers.dataclasses.User


/**
 * User view displays selected User's information.
 * User deletion is invoked here and edit view is navigated to from here.
 *
 * @param user Current User.
 * @param navController Used to navigate back and into editUserView.
 */
@Composable
fun UserView(user: User, navController: NavController) {
    var showDeletePrompt by remember { mutableStateOf(false) }
    var isDeleted by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    Column {
        Header("${user.firstName} ${user.lastName}")
        Row {
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

