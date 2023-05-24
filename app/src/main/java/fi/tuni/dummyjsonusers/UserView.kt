package fi.tuni.dummyjsonusers

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import okhttp3.*
import java.io.IOException


@Composable
fun UserView(user: User, navController: NavController) {
    Column() {
        Header("${user.firstName} ${user.lastName}")
        Row() {
            BackArrow(navController = navController)
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                onClick = { deleteUser(user.id!!) },
                modifier = Modifier.padding(horizontal = 30.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                    contentDescription = "Delete"
                )
            }
        }

    }
}

fun deleteUser(id: Int) {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://dummyjson.com/users/${id}")
        .delete()
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            // Handle request success
            if (response.isSuccessful) {
                Log.d("DEBUG", "DELETE request successful")
                val json = response.body?.string()
                Log.d("DEBUG", json!!)
            } else {
                Log.d("DEBUG", "DELETE request failed")
                // Handle unsuccessful response
            }
            response.close()
        }
    })
}