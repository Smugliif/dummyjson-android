package fi.tuni.dummyjsonusers.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fi.tuni.dummyjsonusers.R
import fi.tuni.dummyjsonusers.api.fetchUsers
import fi.tuni.dummyjsonusers.composables.Header
import fi.tuni.dummyjsonusers.composables.LoadingScreen
import fi.tuni.dummyjsonusers.dataclasses.User


/**
 * User list view
 *
 * @param users List of User objects to be displayed in the Composable List.
 * @param navController Navigation controller declared in Navigation, for navigating between views.
 */
@Composable
fun UserListView(users: List<User>?, navController: NavController) {
    var searchTerm by remember { mutableStateOf("") }
    var usersList by remember { mutableStateOf<List<User>?>(users) }

    LaunchedEffect(Unit) {
        fetchUsers(null, onSuccess = { newUsers ->
            usersList = newUsers
        }, onFailure = {/*TODO*/ })
    }

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Column {
            Header("Users")
            OutlinedTextField(
                value = searchTerm,
                onValueChange = {
                    searchTerm = it
                    fetchUsers(searchKeyword = it, onSuccess = { newUsers ->
                        usersList = newUsers
                    }, onFailure = {/*TODO*/ })
                },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedButton(
                onClick = { navController.navigate("addUserView") },
                modifier = Modifier
                    .align(End)
                    .padding(30.dp, 10.dp)
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "Add"
                )
            }
            if (usersList != null) {
                UserList(usersList!!, navController)

            } else {
                LoadingScreen()
            }
        }
    }
}

/**
 * User list
 *
 * @param users List of User objects to be iterated into UserCard Composables.
 * @param navController Passed to UserCards.
 */
@Composable
fun UserList(users: List<User>, navController: NavController) {
    LazyColumn {
        items(users) { user ->
            UserCard(user, navController)
        }
    }
}

/**
 * User card
 *
 * @param user Current User object.
 * @param navController Used to navigate to this user's specific view on click.
 */
@Composable
fun UserCard(user: User, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { navController.navigate("userView/${user.id}") },
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Row {
                Text("${user.firstName} ${user.lastName}")
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.baseline_more_vert_24),
                    contentDescription = "Forward"
                )
            }

        }
    }
}

