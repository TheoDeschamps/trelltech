package com.trelltech.frontend.ui

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class Post {

    private val client = OkHttpClient()

    fun postCard(
        listId: String,
        name: String,
        desc: String,
        userId: String = "defaultUser",
        callback: (Boolean) -> Unit
    ) {
        val url = "http://10.0.2.2:8080/cards"

        val jsonBody = JSONObject().apply {
            put("listId", listId)
            put("name", name)
            put("desc", desc)
            put("userId", userId)
        }

        val requestBody = jsonBody.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(false)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    callback(it.isSuccessful)
                }
            }
        })
    }
}
