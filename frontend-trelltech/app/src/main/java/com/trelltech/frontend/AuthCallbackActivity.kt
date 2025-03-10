package com.trelltech.frontend

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity

class AuthCallbackActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri: Uri? = intent?.data
        val token = uri?.getQueryParameter("token")
        if (!token.isNullOrEmpty()) {
            SecurityModule.storeToken(this, token)
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
