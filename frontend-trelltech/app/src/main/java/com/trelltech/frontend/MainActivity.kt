package com.trelltech.frontend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonAuth = findViewById<Button>(R.id.buttonAuth)
        val textToken = findViewById<TextView>(R.id.textToken)
        buttonAuth.setOnClickListener {
            startActivity(Intent(this, AuthBrowserActivity::class.java))
        }
        val savedToken = SecurityModule.getToken(this)
        if (!savedToken.isNullOrEmpty()) {
            textToken.text = savedToken
        }
    }
}
