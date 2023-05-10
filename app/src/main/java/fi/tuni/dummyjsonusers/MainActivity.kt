package fi.tuni.dummyjsonusers

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fi.tuni.dummyjsonusers.ui.theme.DummyJSONUsersTheme
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
                    Greeting("Android")
                    Button(onClick = { fetchUsers() }) {
                        Text("Fetch Users")
                    }
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
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

fun fetchUsers() {
    val url = "https://dummyjson.com/users"
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .build()
    client.newCall(request).enqueue(object: Callback {
        @Override
        override fun onFailure(call: Call, e: IOException) {
            Log.d("DEBUG","Error while fetching users")
        }
        @Override
        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) {
                Log.d("DEBUG","Something went wrong")
                return
            }
            Log.d("DEBUG","Success")
            val json = response.body?.string()
            Log.d("DEBUG", json!!)
            if (json != null) {
                val jsonObject = JSONObject(json)
                Log.d("DEBUG","json not null")
            }
        }
    })
    val users = mutableListOf<User>()

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DummyJSONUsersTheme {
        Greeting("Android")
    }
}