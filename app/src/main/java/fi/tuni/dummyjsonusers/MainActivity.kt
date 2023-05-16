package fi.tuni.dummyjsonusers

import androidx.compose.runtime.Composable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fi.tuni.dummyjsonusers.ui.theme.DummyJSONUsersTheme
import kotlinx.coroutines.CompletableDeferred
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
                    MyApp()
                }
            }
        }
    }
}

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String
)

@Composable
fun MyApp() {
    var users by remember { mutableStateOf<List<User>?>(null) }

    LaunchedEffect(Unit) {
        users = fetchUsers()
    }

    Surface(color = MaterialTheme.colors.background) {
        if (users != null) {
            UserList(users!!)
        } else {
            LoadingScreen()
        }
    }
}

@Composable
fun UserList(users: List<User>) {
    LazyColumn {
        items(users) { user ->
            Text("${user.firstName} ${user.lastName}")
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}

//How to return what I get, that is the question
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
        MyApp()
    }
}