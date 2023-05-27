package fi.tuni.dummyjsonusers

import android.util.Log
import fi.tuni.dummyjsonusers.dataclasses.User
import kotlinx.coroutines.CompletableDeferred
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

// Fetches users from the api
suspend fun fetchUsers(keyword: String?): List<User>? {
    val users = mutableListOf<User>()
    val deferred = CompletableDeferred<List<User>?>()

    // Fetch URL
    var url = "https://dummyjson.com/users"
    // If there is a keyword search users with keyword
    if (keyword != null) {
        url = "https://dummyjson.com/users/search?q=${keyword}"
    }

    // Create an instance of the client and request
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .build()

    // Execute request
    client.newCall(request).enqueue(object : Callback {
        @Override
        override fun onFailure(call: Call, e: IOException) {
            Log.d("DEBUG", "Error while fetching users")
            deferred.complete(null)
        }

        @Override
        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) {
                Log.d("DEBUG", response.message)
                deferred.complete(null)
                return
            }
            Log.d("DEBUG", "Success")
            val json = response.body?.string()
            if (json != null) {
                // Add users from JSON to list
                val jsonObject = JSONObject(json)
                val usersJSONArray = jsonObject.getJSONArray("users")
                for (i in 0 until usersJSONArray.length()) {
                    val userObject = usersJSONArray.getJSONObject(i)

                    val newUser = User(
                        userObject.getInt("id"),
                        userObject.getString("firstName"),
                        userObject.getString("lastName")
                    )
                    users.add(newUser)
                }
            }
            deferred.complete(users)
        }
    })

    return deferred.await()
}