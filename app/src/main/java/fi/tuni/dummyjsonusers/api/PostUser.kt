package fi.tuni.dummyjsonusers.api

import android.util.Log
import fi.tuni.dummyjsonusers.dataclasses.User
import fi.tuni.dummyjsonusers.isValidUser
import okhttp3.*
import java.io.IOException

/**
 * Post user sends a new user to the backend.
 *
 * It first checks that the user is valid and then invokes a callback depending on success.
 * Uses OkHttp for the API call.
 *
 * @param user User object that will be sent to the backend.
 * @param onSuccess Callback lambda invoked on success of the POST request.
 * @param onFailure Callback lambda invoked when encountering an error with the POST request.
 */
fun postUser(user: User, onSuccess: () -> Unit, onFailure: () -> Unit) {
    val url = "https://dummyjson.com/users/add"
    // Check that given user is valid
    if (!isValidUser(user)) {
        onFailure()
        return
    }

    // Create an OkHttpClient instance
    val client = OkHttpClient()

    // Create a request body with the names
    val requestBody = FormBody.Builder()
        .add("firstName", user.firstName)
        .add("lastName", user.lastName)
        .build()

    // Create a POST request with the desired URL and request body
    val request = Request.Builder()
        .url(url)
        .post(requestBody)
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