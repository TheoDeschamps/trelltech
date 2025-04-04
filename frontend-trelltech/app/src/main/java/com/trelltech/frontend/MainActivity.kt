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
        val savedToken = SecurityModule.getToken(this)

        if (!savedToken.isNullOrEmpty()) {
            buttonAuth.visibility = android.view.View.GONE
            textToken.text = "Connected!"
            textToken.setOnClickListener {
                textToken.text = savedToken
            }
        } else {
            textToken.text = "Not connected"
            buttonAuth.visibility = android.view.View.VISIBLE
            buttonAuth.setOnClickListener {
                startActivity(Intent(this, AuthBrowserActivity::class.java))
            }
        }
    }
}
