package com.trelltech.frontend.ui.screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.trelltech.frontend.R
import com.trelltech.frontend.ui.AuthBrowserActivity
import com.trelltech.frontend.SecurityModule

class AuthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val authButton = view.findViewById<Button>(R.id.buttonConnectTrello)
        authButton.setOnClickListener {
            startActivity(Intent(requireContext(), AuthBrowserActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        val token = SecurityModule.getToken(requireContext())
        if(!token.isNullOrEmpty()) {
            findNavController().navigate(R.id.action_auth_to_home)
        }
    }
}
