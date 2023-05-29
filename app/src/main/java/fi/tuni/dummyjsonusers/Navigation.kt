package fi.tuni.dummyjsonusers

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fi.tuni.dummyjsonusers.api.fetchUsers
import fi.tuni.dummyjsonusers.dataclasses.User
import fi.tuni.dummyjsonusers.views.AddUserView
import fi.tuni.dummyjsonusers.views.EditUserView
import fi.tuni.dummyjsonusers.views.UserListView
import fi.tuni.dummyjsonusers.views.UserView

/**
 * Navigation
 *
 * Composable creating the NavController object and NavHost for the app's navigation.
 * A List of User objects is created here and filled with data from the backend.
 *
 */
@Preview
@Composable
fun Navigation() {
    val navController = rememberNavController()
    var users by remember { mutableStateOf<List<User>?>(null) }

    LaunchedEffect(Unit) {
        fetchUsers(null, onSuccess = { newUsers ->
            users = newUsers
        }, onFailure = {/*TODO*/})
    }

    NavHost(navController = navController, startDestination = "userList") {
        composable("userList") {
            UserListView(users, navController)
        }
        /**
         * User IDs are used to navigate to views specific to each user.
         */
        composable("userView/{userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.IntType
                }
            ))
        { entry ->
            val userId = entry.arguments?.getInt("userId")
            val user = users?.find { it.id == userId }
            if (user != null) {
                UserView(user, navController)
            } //TODO Error handling
        }
        composable("addUserView") {
            AddUserView(navController)
        }
        composable("editUserView/{userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.IntType
                }
            ))
        { entry ->
            val userId = entry.arguments?.getInt("userId")
            val user = users?.find { it.id == userId }
            if (user != null) {
                EditUserView(user, navController)
            }
        }
    }
}