package fi.tuni.dummyjsonusers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


class UserListView {

    @Composable
    fun Screen(users: List<User>?, navController: NavController) {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            Column() {
                Header("Users")
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
                if (users != null) {
                    UserList(users, navController)

                } else {
                    LoadingScreen()
                }
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

    @Composable
    fun UserList(users: List<User>, navController: NavController) {
        LazyColumn {
            items(users) { user ->
                UserCard(user, navController)
            }
        }
    }
}