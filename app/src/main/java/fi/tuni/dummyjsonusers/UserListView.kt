package fi.tuni.dummyjsonusers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch


class UserListView: ViewModel() {

    @Composable
    fun Screen(users: List<User>?, navController: NavController) {
        var searchTerm by remember { mutableStateOf("") }
        var usersList by remember { mutableStateOf<List<User>?>(users) }

        LaunchedEffect(Unit) {
            usersList = fetchUsers(null)
        }

        Surface(
            color = MaterialTheme.colors.background
        ) {
            Column() {
                Header("Users")
                OutlinedTextField(
                    value = searchTerm,
                    onValueChange = {
                        searchTerm = it
                        viewModelScope.launch { usersList = fetchUsers(searchTerm) }
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
    @Composable
    fun UserList(users: List<User>, navController: NavController) {
        LazyColumn {
            items(users) { user ->
                UserCard(user, navController)
            }
        }
    }

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
                Text("${user.firstName} ${user.lastName}")
            }
        }
    }


}