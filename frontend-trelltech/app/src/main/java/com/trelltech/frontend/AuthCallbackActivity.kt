package com.trelltech.frontend

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.*

class AuthCallbackActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri: Uri? = intent?.data
        val fragment = uri?.fragment
        val token = fragment?.substringAfter("token=")

        if (!token.isNullOrEmpty()) {
            SecurityModule.storeToken(this, token)
            sendTokenToBackend(token)
        }

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun sendTokenToBackend(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()

            val json = """
                {
                    "userId": "defaultUser",
                    "token": "$token"
                }
            """.trimIndent()

            val mediaType = "application/json".toMediaType()
            val requestBody = json.toRequestBody(mediaType)

            val request = Request.Builder()
                .url("http://10.0.2.2:8080/tokens")
                .post(requestBody)
                .build()

            try {
                val response = client.newCall(request).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AuthCallbackActivity, "Token envoyé au backend", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AuthCallbackActivity, "Erreur lors de l'envoi: ${response.code}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AuthCallbackActivity, "Erreur réseau : ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
