package com.trelltech.frontend

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.browser.customtabs.CustomTabsIntent

class AuthBrowserActivity : ComponentActivity() {
    private val baseUrl = "http://10.0.2.2:8080"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_browser)
        val url = "$baseUrl/auth/login"
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
        finish()
    }
}
