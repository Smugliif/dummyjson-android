package fi.tuni.dummyjsonusers.api

import android.util.Log
import okhttp3.*
import java.io.IOException

/**
 * Delete user deletes given users by ID.
 *
 * Send DELETE request to API with target user's ID then invoke callback depending on success.
 *
 * @param id Target user's ID.
 * @param onSuccess Callback lambda invoked on success.
 * @param onFailure Callback lambda invoked on failure.
 */
// Send DELETE request to API with target user's ID
fun deleteUser(id: Int, onSuccess: () -> Unit, onFailure: () -> Unit) {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://dummyjson.com/users/${id}")
        .delete()
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            onFailure()
        }

        override fun onResponse(call: Call, response: Response) {
            // Handle request success
            if (response.isSuccessful) {
                Log.d("DEBUG", "DELETE request successful")
                val json = response.body?.string()
                Log.d("DEBUG", json!!)
                onSuccess()
            } else {
                Log.d("DEBUG", "DELETE request failed")
                onFailure()
            }
            response.close()
        }
    })
}