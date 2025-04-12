package com.trelltech.frontend.ui

import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import kotlin.collections.List

class Get {
    private val client = OkHttpClient()
    fun getBoards(callback: (List<JSONObject>) -> Unit) {
        val request = Request.Builder()
            .url("http://10.0.2.2:8080/boards/defaultUser")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e:IOException){
                e.printStackTrace()
                callback(emptyList())
            }
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) throw IOException("Unexpected code $response")
                    val responseBody = it.body?.string()
                    val jsonArray = JSONArray(responseBody)
                    val boards = mutableListOf<JSONObject>()
                    for (i in 0 until jsonArray.length()) {
                        val board = jsonArray.getJSONObject(i)
                        boards.add(board)
                    }
                    callback(boards)
                }
            }
        })
    }

    fun getLists(boardId: String, callback: (List<JSONObject>) -> Unit) {
        val url = "http://10.0.2.2:8080/lists/boards/$boardId?userId=defaultUser"
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(emptyList())
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) throw IOException("Unexpected code $response")
                    val responseBody = it.body?.string()
                    val jsonArray = JSONArray(responseBody)
                    val lists = mutableListOf<JSONObject>()
                    for (i in 0 until jsonArray.length()) {
                        val list = jsonArray.getJSONObject(i)
                        lists.add(list)
                    }
                    callback(lists)
                }
            }
        })
    }

    fun getCards(listId: String, callback: (List<JSONObject>) -> Unit) {
        val url = "http://10.0.2.2:8080/lists/$listId/cards?userId=defaultUser"
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(emptyList())
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) throw IOException("Unexpected code $response")
                    val responseBody = it.body?.string()
                    val jsonArray = JSONArray(responseBody)
                    val cards = mutableListOf<JSONObject>()
                    for (i in 0 until jsonArray.length()) {
                        val card = jsonArray.getJSONObject(i)
                        cards.add(card)
                    }
                    callback(cards)
                }
            }
        })
    }
}