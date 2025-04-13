package com.trelltech.frontend.ui

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class Put {
    private val client = OkHttpClient()

    fun updateBoard(boardId: String, name: String, desc: String, userId: String = "defaultUser", callback: (Boolean) -> Unit) {
        val url = "http://10.0.2.2:8080/boards/$boardId?userId=$userId"

        val jsonBody = JSONObject().apply {
            put("name", name)
            put("desc", desc)
        }

        val requestBody = jsonBody.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .put(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Put", "❌ Erreur update board", e)
                callback(false)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Put", "🟢 Update board: ${response.code}")
                callback(response.isSuccessful)
            }
        })
    }

    fun updateList(
        listId: String,
        name: String,
        userId: String = "defaultUser",
        callback: (Boolean) -> Unit
    ) {
        val url = "http://10.0.2.2:8080/lists/$listId"

        val formBody = FormBody.Builder()
            .add("name", name)
            .add("userId", userId)
            .build()

        val request = Request.Builder()
            .url(url)
            .put(formBody) // 💥 Important : PUT + FormBody
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Put", "❌ Erreur update liste", e)
                callback(false)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Put", "🟢 Update liste: ${response.code}")
                callback(response.isSuccessful)
            }
        })
    }

    fun updateCard(cardId: String, name: String, desc: String, userId: String = "defaultUser", callback: (Boolean) -> Unit) {
        val url = "http://10.0.2.2:8080/cards/$cardId"

        val jsonBody = JSONObject().apply {
            put("name", name)
            put("desc", desc)
            put("userId", userId)
        }

        val requestBody = jsonBody.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .patch(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Put", "❌ Erreur update carte", e)
                callback(false)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Put", "🟢 Update carte: ${response.code}")
                callback(response.isSuccessful)
            }
        })
    }
}
