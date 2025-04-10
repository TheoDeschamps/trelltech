package com.trelltech.frontend.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.trelltech.frontend.R
import com.trelltech.frontend.SecurityModule

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val token = SecurityModule.getToken(requireContext())
        if (token.isNullOrEmpty()) {
            findNavController().navigate(R.id.action_home_to_auth)
        }

        val buttonGo = view.findViewById<Button>(R.id.buttonGoToBoards)
        buttonGo.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_boards)
        }
    }
}