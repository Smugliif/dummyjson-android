package fi.tuni.dummyjsonusers

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CompletableDeferred
import okhttp3.*
import java.io.IOException

fun putUser(user: User, onSuccess: () -> Unit, onFailure: () -> Unit) {
    val url = "https://dummyjson.com/users/${user.id}"
    // Check that given user is valid
    if (!isValidUser(user)) {
        onFailure()
        return
    }

    // Create an OkHttpClient instance
    val client = OkHttpClient()

    // Create a request body with the data to be sent
    val requestBody = FormBody.Builder()
        .add("firstName", user.firstName)
        .add("lastName", user.lastName)
        .build()

    // Create a POST request with the desired URL and request body
    val request = Request.Builder()
        .url(url)
        .put(requestBody)
        .build()

    // Execute the request and process the response
    client.newCall(request).enqueue(object : Callback {
        @Override
        override fun onFailure(call: Call, e: IOException) {
            Log.d("DEBUG", "Error while fetching users")
            onFailure()
        }

        @Override
        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) {
                Log.d("DEBUG", "${response.code}")
                onFailure()
                return
            }
            Log.d("DEBUG", "Success")
            val json = response.body?.string()
            Log.d("DEBUG", json!!)
            onSuccess()
            response.close()
        }
    })
}



