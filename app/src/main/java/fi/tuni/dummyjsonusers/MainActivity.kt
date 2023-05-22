package fi.tuni.dummyjsonusers

import androidx.compose.runtime.Composable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fi.tuni.dummyjsonusers.ui.theme.DummyJSONUsersTheme
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DummyJSONUsersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyNavigation()
                }
            }
        }
    }
}

@Composable
fun MyNavigation() {
    val navController = rememberNavController()
    var users by remember { mutableStateOf<List<User>?>(null) }

    LaunchedEffect(Unit) {
        users = fetchUsers()
    }

    NavHost(navController = navController, startDestination = "userList") {
        composable("userList") {
            UserScreen(users, navController)
        }
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
                UserView(user)
            }
        }
    }
}

@Composable
fun UserScreen(users: List<User>?, navController: NavController) {
    Surface(
        color = MaterialTheme.colors.background
    ) {
        if (users != null) {
            UserList(users, navController)
        } else {
            LoadingScreen()
        }
    }
}

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String
)

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

@Composable
fun UserView(user: User) {
    Column() {
        Text(text = "Name: ${user.firstName}")
    }
}

@Composable
fun LoadingScreen() {
    Column(Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}

//fetches users from the api
suspend fun fetchUsers(): List<User>? {
    val deferred = CompletableDeferred<List<User>?>()

    val url = "https://dummyjson.com/users"
    val users = mutableListOf<User>()
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .build()
    client.newCall(request).enqueue(object: Callback {
        @Override
        override fun onFailure(call: Call, e: IOException) {
            Log.d("DEBUG","Error while fetching users")
            deferred.complete(null)
        }
        @Override
        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) {
                Log.d("DEBUG","Something went wrong")
                deferred.complete(null)
                return
            }
            Log.d("DEBUG","Success")
            val json = response.body?.string()
            if (json != null) {
                val jsonObject = JSONObject(json)
                val usersArray = jsonObject.getJSONArray("users")
                for (i in 0 until usersArray.length()) {
                    val userObject = usersArray.getJSONObject(i)

                    val newUser = User(
                        userObject.getInt("id"),
                        userObject.getString("firstName"),
                        userObject.getString("lastName")
                    )
                    Log.d("DEBUG", newUser.toString())
                    users.add(newUser)
                }
            }
            deferred.complete(users)
        }
    })

    return deferred.await()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DummyJSONUsersTheme {
        MyNavigation()
    }
}