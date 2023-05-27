package fi.tuni.dummyjsonusers.api

import android.util.Log
import fi.tuni.dummyjsonusers.dataclasses.User
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

// Fetches users from the api
fun fetchUsers(searchKeyword: String?, onSuccess: (List<User>) -> Unit, onFailure: () -> Unit = {}) {
    val users = mutableListOf<User>()
    // Fetch URL
    var url = "https://dummyjson.com/users"
    // If there is a keyword search users with keyword
    if (searchKeyword != null) {
        url = "https://dummyjson.com/users/search?q=${searchKeyword}"
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
            onFailure()
            return
        }

        @Override
        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) {
                Log.d("DEBUG", response.message)
                onFailure()
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
            onSuccess(users)
        }
    })

}