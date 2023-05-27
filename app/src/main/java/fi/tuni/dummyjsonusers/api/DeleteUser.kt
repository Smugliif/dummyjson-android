package fi.tuni.dummyjsonusers

import android.util.Log
import okhttp3.*
import java.io.IOException

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