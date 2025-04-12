package com.trelltech.frontend.ui

import android.util.Log
import okhttp3.*
import java.io.IOException

class Delete {
    private val client = OkHttpClient()

    fun deleteBoard(boardId: String, userId: String, callback: (Boolean) -> Unit) {
        val url = "http://10.0.2.2:8080/boards/$boardId?userId=$userId"
        val request = Request.Builder()
            .url(url)
            .delete()
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(false)
            }

            override fun onResponse(call: Call, response: Response) {
                callback(response.isSuccessful)
            }
        })
    }

    fun deleteCard(cardId: String, userId: String = "defaultUser", callback: (Boolean) -> Unit) {
        val url = "http://10.0.2.2:8080/cards/$cardId?userId=$userId"
        val request = Request.Builder()
            .url(url)
            .delete()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(false)
            }

            override fun onResponse(call: Call, response: Response) {
                callback(response.isSuccessful)
            }
        })
    }

    fun deleteList(listId: String, userId: String = "defaultUser", callback: (Boolean) -> Unit) {
        val url = "http://10.0.2.2:8080/lists/$listId?userId=$userId"

        val request = Request.Builder()
            .url(url)
            .delete()
            .build()

        Log.d("Delete", "🔴 Suppression de la liste id=$listId, userId=$userId")

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(false)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Delete", "Réponse suppression liste: ${response.code}")
                callback(response.isSuccessful)
            }
        })
    }

}
