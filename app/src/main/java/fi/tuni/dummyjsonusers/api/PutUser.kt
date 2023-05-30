package fi.tuni.dummyjsonusers.api

import android.util.Log
import fi.tuni.dummyjsonusers.dataclasses.User
import fi.tuni.dummyjsonusers.isValidUser
import okhttp3.*
import java.io.IOException

/**
 * Sends a modified user to the backend.
 * First checks that the user is valid and then invokes callback depending on success.
 * Uses OkHttp for the API call.
 *
 * @param user Modified user object.
 * @param onSuccess Callback lambda invoked on success of PUT request.
 * @param onFailure Callback lambda invoked when encountering an error with the PUT request.
 */
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



